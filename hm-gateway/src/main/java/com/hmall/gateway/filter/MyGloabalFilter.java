package com.hmall.gateway.filter;

import com.hmall.common.exception.UnauthorizedException;
import com.hmall.common.utils.CollUtils;
import com.hmall.gateway.config.AuthProperties;
import com.hmall.gateway.utils.JwtTool;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.RequestPath;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @PROJECT_NAME: hmall
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2024/5/14 22:32
 */
@Component
@RequiredArgsConstructor
@EnableConfigurationProperties(AuthProperties.class)
public class MyGloabalFilter implements GlobalFilter, Ordered {

    private final JwtTool jwtTool;

    private final AuthProperties authProperties;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        // 根据请求头做判断
        if(isExclude(request.getPath())){
            System.out.println("被我放行啦");
            return chain.filter(exchange);
        }

        List<String> authorization = request.getHeaders().get("authorization");
        String token = null;
        if(!CollUtils.isEmpty(authorization)){
            token = authorization.get(0);
        }

        // 拿到token去解析token
        Long userId = null;
        try {
            userId = jwtTool.parseToken(token);
        }catch (UnauthorizedException unauthorizedException){
            System.out.println("捕获到异常 ");
            ServerHttpResponse serverHttpResponse = exchange.getResponse();
            serverHttpResponse.setRawStatusCode(401);
            return serverHttpResponse.setComplete();
        }

        // TODO 将用户值往后传递
        String userInfo = userId.toString();
        ServerWebExchange build = exchange.mutate().request(b -> b.header("user-info", userInfo)).build();
//        System.out.println("用户ID为：" + userId);
//        System.out.println("GlobalFilter执行了");
        return chain.filter(build);
    }

    private boolean isExclude(RequestPath path) {
        for(String patternPath : authProperties.getExcludePaths()){
            if(antPathMatcher.match(patternPath, path.toString())){
                return true;
            }
        }
        return false;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
