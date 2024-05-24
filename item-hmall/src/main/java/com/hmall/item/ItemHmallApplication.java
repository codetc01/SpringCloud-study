package com.hmall.item;

import com.hmall.api.config.defaultClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients(defaultConfiguration = defaultClientConfig.class)
@SpringBootApplication
public class ItemHmallApplication {
    public static void main(String[] args) {
        SpringApplication.run(ItemHmallApplication.class, args);
    }

}
