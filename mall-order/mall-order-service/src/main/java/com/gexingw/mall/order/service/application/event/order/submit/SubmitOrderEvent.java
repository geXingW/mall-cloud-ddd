package com.gexingw.mall.order.service.application.event.order.submit;

import com.gexingw.mall.order.service.domain.model.Order;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * mall-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/2 16:45
 */
public class SubmitOrderEvent extends ApplicationEvent {

    @Getter
    private final Order order;

    public SubmitOrderEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }

}
