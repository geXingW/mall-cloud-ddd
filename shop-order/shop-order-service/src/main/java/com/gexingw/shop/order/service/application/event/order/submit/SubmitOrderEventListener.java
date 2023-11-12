package com.gexingw.shop.order.service.application.event.order.submit;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * shop-cloud-ddd.
 *
 * @author GeXingW
 * @date 2023/11/12 12:46
 */
@Component
public class SubmitOrderEventListener {

    @EventListener
    public void persistOrderListener(SubmitOrderEvent event) {
        System.out.println(Thread.currentThread().getName() + " " + this.getClass().getName() + " persistOrderListener.");
    }

    @Async
    @TransactionalEventListener
    public void broadcastOrderListener(SubmitOrderEvent event) {
        System.out.println(Thread.currentThread().getName() + " " + this.getClass().getName() + " broadcastOrderListener.");
    }

}
