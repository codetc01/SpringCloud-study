package com.hmall.order.listener;

import com.hmall.order.domain.po.Order;
import com.hmall.order.service.IOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @PROJECT_NAME: hmall
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2024/5/21 23:21
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MQListener {

    private final IOrderService iOrderService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "trade.pay.success.queue", durable = "true"),
            exchange = @Exchange(name = "pay.direct", type = ExchangeTypes.DIRECT),
            key = "pay.success"
    ))
    public void getMessageFromDQ1(Long orderId){
        // 查询数据库，获得订单当前的状态
        Order order = iOrderService.getById(orderId);

        if(order.getStatus() == null || order.getStatus() != 1){
            return;
        }

        iOrderService.markOrderPaySuccess(orderId);
    }

}
