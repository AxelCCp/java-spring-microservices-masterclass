package com.indi.order.model.service;

import com.indi.order.model.dto.OrderCreatedEvent;
import com.indi.order.model.dto.OrderItemDTO;
import com.indi.order.model.dto.OrderResponse;
import com.indi.order.model.entity.*;
import com.indi.order.model.repository.OrderRepository;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final CartItemService cartItemService;
    private final OrderRepository orderRepository;
    //private final RabbitTemplate rabbitTemplate;
    private final StreamBridge streamBridge;

    public OrderService(CartItemService cartItemService, OrderRepository orderRepository, StreamBridge streamBridge) {
        this.cartItemService =  cartItemService;
        this.orderRepository = orderRepository;
        //this.rabbitTemplate = rabbitTemplate;
        this.streamBridge = streamBridge;
    }

    @Transactional
    public Optional<OrderResponse> createOrder(String userId) {

        List<CartItem> cartItemList = this.cartItemService.getCart(userId);
        if(cartItemList.isEmpty()) {
            return Optional.empty();
        }

        /*Optional<User> user_op = this.userRepository.findById(Long.valueOf(userId));
        if(user_op.isEmpty()) {
            return Optional.empty();
        }
        User user = user_op.get();*/

        BigDecimal totalPrice = cartItemList.stream().map(cartItem -> cartItem.getPrice()).reduce(BigDecimal.ZERO, (accumulator, price ) -> accumulator.add(price));
        Order order = new Order();
        order.setUserId(userId);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItemList.stream().map(item -> new OrderItem(null, item.getProductId(), item.getQuantity(), item.getPrice(), order)).collect(Collectors.toList());

        order.setItems(orderItems);

        Order order_db = this.orderRepository.save(order);

        this.cartItemService.cleanCart(userId);

        OrderCreatedEvent event = new OrderCreatedEvent(
            order_db.getId(), 
            order_db.getUserId(),
            order_db.getStatus(), 
            this.mapToOrderItemDTOs(order_db.getItems()), 
            order_db.getTotalAmount(),
            order_db.getCreateAt());
        

        //this.rabbitTemplate.convertAndSend("order.exchange", "order.tracking", Map.of("orderId", order_db.getId(), "status", "CREATED"));

        //this.rabbitTemplate.convertAndSend("order.exchange", "order.tracking", event);

        //291
        streamBridge.send("createOrder-out-0", event);

        return Optional.of(this.mapToOrderResponse(order_db));

    }

    private List<OrderItemDTO> mapToOrderItemDTOs(List<OrderItem>items) {
        return items.stream()
        .map(item -> new OrderItemDTO(
            item.getId(), 
            item.getProductId(), 
            item.getQuantity(), 
            item.getPrice(), 
            item.getPrice().multiply(new BigDecimal(item.getQuantity()))))
            .collect(Collectors.toList());
    }

    private OrderResponse mapToOrderResponse(Order orderDb) {

        return new OrderResponse(orderDb.getId(), orderDb.getTotalAmount(), orderDb.getStatus(),
                                 orderDb.getItems().stream().map(orderItem -> new OrderItemDTO(
                                                                                    orderItem.getId(),
                                                                                    orderItem.getProductId(),
                                                                                    orderItem.getQuantity(),
                                                                                    orderItem.getPrice(),
                                                                                    orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                                 )).toList(),
                                 orderDb.getCreateAt());
    }
}
