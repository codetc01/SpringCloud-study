package com.hmall.pay;

import com.hmall.api.config.defaultClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients(basePackages = "com.hmall.api.client",  defaultConfiguration = defaultClientConfig.class)
public class PayControllerApplication {

    public static void main(String[] args) {
        SpringApplication.run(PayControllerApplication.class, args);
    }

}
