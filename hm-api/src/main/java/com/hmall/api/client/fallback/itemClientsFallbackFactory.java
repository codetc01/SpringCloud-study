package com.hmall.api.client.fallback;

import com.hmall.api.client.itemClients;
import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import com.hmall.common.exception.BizIllegalException;
import com.hmall.common.utils.CollUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Collection;
import java.util.List;

/**
 * @PROJECT_NAME: hmall
 * @DESCRIPTION:
 * @USER: Administrator
 * @DATE: 2024/5/17 17:07
 */
@Slf4j
public class itemClientsFallbackFactory implements FallbackFactory<itemClients> {

    @Override
    public itemClients create(Throwable cause) {
        return new itemClients() {
            // 这里就是自定义的fallback
            @Override
            public List<ItemDTO> queryByIds(Collection<Long> ids) {
                log.info("远程调用失败,参数{}", ids, cause);
                return CollUtils.emptyList();
            }

            @Override
            public void deductStock(List<OrderDetailDTO> ids) {
                throw new BizIllegalException(cause);
            }
        };
    }
}
