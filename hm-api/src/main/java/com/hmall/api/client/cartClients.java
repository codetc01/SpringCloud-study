package com.hmall.api.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;

@FeignClient(value = "cart-service")
public interface cartClients {
    @DeleteMapping("/carts")
    void deleteByIds(@RequestParam("ids")Collection<Long> ids);
}
