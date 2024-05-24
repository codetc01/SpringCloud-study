package com.hmall.api.config;

import com.hmall.api.client.fallback.itemClientsFallbackFactory;
import com.hmall.common.utils.UserContext;
import feign.Logger;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Bean;

/**
 * @PROJECT_NAME: hmall
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2024/5/13 17:00
 */
public class defaultClientConfig {

    @Bean
    public Logger.Level feignLevel(){
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor getUserInfoInterceptor(){
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate requestTemplate) {
                Long user = UserContext.getUser();
                if(user != null){
                    requestTemplate.header("user-info", user.toString());
                }
            }
        };
    }

    @Bean
    public itemClientsFallbackFactory itemClientsFallbackFactory() {
        return new itemClientsFallbackFactory();
    }
}
