package com.indi.order.model.service;

import com.indi.order.model.dto.OrderItemDTO;
import com.indi.order.model.dto.OrderResponse;
import com.indi.order.model.entity.*;
import com.indi.order.model.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final CartItemService cartItemService;
    private final OrderRepository orderRepository;

    public OrderService(CartItemService cartItemService, OrderRepository orderRepository) {
        this.cartItemService =  cartItemService;
        this.orderRepository = orderRepository;
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

        return Optional.of(this.mapToOrderResponse(order_db));

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
