package com.hmall.cart.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @PROJECT_NAME: hmall
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2024/5/16 15:24
 */
@Data
@Component
@ConfigurationProperties(prefix = "hm.cart")
public class Cartpropertites {

    private Integer maxItems;

}
