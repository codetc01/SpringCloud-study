package com.hmall.gateway.route;

import cn.hutool.json.JSONUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.hmall.common.utils.CollUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionWriter;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;

/**
 * @PROJECT_NAME: hmall
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2024/5/16 16:31
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DynamicRouteLoader {

    private final NacosConfigManager nacosConfigManager;
    private final RouteDefinitionWriter writer;

    private final String dataId = "gateway-routes.json";
    private final String group = "DEFAULT_GROUP";

    private final Set<String> routeIds = new HashSet<>();

    @PostConstruct
    public void initRouteConfigListener() throws NacosException {
        nacosConfigManager.getConfigService().getConfigAndSignListener(dataId, group, 5000,
                new Listener() {
                    @Override
                    public Executor getExecutor() {
                        return null;
                    }

                    @Override
                    public void receiveConfigInfo(String s) {
                        System.out.println("hello");
                        // 在这里监听到路由变化，做相应处理
                        List<RouteDefinition> routeDefinitions = JSONUtil.toList(s, RouteDefinition.class);

                        // 将原始路由表中的数据全部移除
                        for (String routeId : routeIds) {
                            writer.delete(Mono.just(routeId));
                        }
                        routeIds.clear();

                        if (CollUtils.isEmpty(routeDefinitions)) {
                            // 无新路由配置，直接结束
                            return;
                        }

                        for (RouteDefinition routeDefinition : routeDefinitions) {
                            writer.save(Mono.just(routeDefinition)).subscribe();
                            routeIds.add(routeDefinition.getId());
                        }

                    }
                });
    }

}
