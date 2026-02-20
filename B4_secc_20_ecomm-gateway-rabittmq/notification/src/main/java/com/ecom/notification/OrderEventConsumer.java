package com.ecom.notification;

import java.util.Map;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import com.ecom.notification.payload.OrderCreatedEvent;
import com.ecom.notification.payload.OrderStatus;

@Service
public class OrderEventConsumer {

    private final AmqpAdmin amqpAdmin;

    OrderEventConsumer(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    //259-configuracion de listener
    /* 
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void  handleOrderEvent(Map<String, Object>orderEvent) {
        System.out.println("Received order event: " + orderEvent);
        Long orderId = Long.valueOf(orderEvent.get("orderId").toString());
        String orderStatus = orderEvent.get("status").toString();
        System.out.println("Order ID: " + orderId);
        System.out.println("Order status: " + orderStatus);

    
        // AQUI SE DEBEN LLEVAR A CABO ESTE TIPO DE ACCIONES:
        // actualizar bbdd.
        // envio de notificaciones.
        // envio de emails. 
        // generar facturas.
        // envio de notificacion al vendedor.
    }
    */

    //260
    //version usando dto 
    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void  handleOrderEvent(OrderCreatedEvent orderEvent) {
        System.out.println("Received order event: " + orderEvent);
        Long orderId = orderEvent.getOrderId();
        OrderStatus orderStatus = orderEvent.getStatus();
        System.out.println("Order ID: " + orderId);
        System.out.println("Order status: " + orderStatus);

    
        // AQUI SE DEBEN LLEVAR A CABO ESTE TIPO DE ACCIONES:
        // actualizar bbdd.
        // envio de notificaciones.
        // envio de emails. 
        // generar facturas.
        // envio de notificacion al vendedor.
        


    }
}
