package com.hmall.api.client;


import com.hmall.api.client.fallback.itemClientsFallbackFactory;
import com.hmall.api.config.defaultClientConfig;
import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Collection;
import java.util.List;

@FeignClient(value = "item-service", configuration = defaultClientConfig.class, fallbackFactory = itemClientsFallbackFactory.class)
public interface itemClients {

    @GetMapping("/items")
    List<ItemDTO> queryByIds(@RequestParam("ids") Collection<Long> ids);

    @PutMapping("/items/stock/deduct")
    void deductStock(@RequestBody List<OrderDetailDTO> ids);

}