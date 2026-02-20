package com.indi.order.model.service;

import com.indi.order.clients.ProductServiceClient;
import com.indi.order.clients.UserServiceClient;
import com.indi.order.model.dto.CartItemRequest;
import com.indi.order.model.dto.ProductResponse;
import com.indi.order.model.dto.UserResponse;
import com.indi.order.model.entity.CartItem;
import com.indi.order.model.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;


@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;

    public CartItemService(CartItemRepository cartItemRepository, ProductServiceClient productServiceClient, UserServiceClient userServiceClient) {
        this.cartItemRepository = cartItemRepository;
        this.productServiceClient = productServiceClient;
        this.userServiceClient = userServiceClient;
    }

    @Transactional
    public Boolean addToCart(String userId, CartItemRequest cartItemRequest) {
        ProductResponse productResponse = this.productServiceClient.getProductDetails(cartItemRequest.getProduct_id());
        if(productResponse == null || productResponse.getStockQuantity() < cartItemRequest.getQuantity()) {
            return Boolean.FALSE;
        }
        UserResponse userResponse = this.userServiceClient.getUserDetails(userId);
        if(userResponse == null) {
            return  Boolean.FALSE;
        }
       
        CartItem cartItem_db = this.cartItemRepository.findByUserIdAndProductId(userId, cartItemRequest.getProduct_id());
        if(cartItem_db != null) {
            cartItem_db.setQuantity(cartItem_db.getQuantity() + cartItemRequest.getQuantity());
            cartItem_db.setPrice(BigDecimal.valueOf(1000.00));
            this.cartItemRepository.save(cartItem_db);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUserId(userId);
            cartItem.setProductId(cartItemRequest.getProduct_id());
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1000.00));
            this.cartItemRepository.save(cartItem);
        }
        return true;
    }

    @Transactional
    public Boolean deleteIntemFromCart(String userId, String productId) {
      CartItem cartItem = this.cartItemRepository.findByUserIdAndProductId(userId, String.valueOf(productId));
        if(cartItem != null) {
            this.cartItemRepository.delete(cartItem);
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Transactional(readOnly = true)
    public List<CartItem> getCart(String userId) {
       return this.cartItemRepository.findByUserId(userId);
    }

    public void cleanCart(String userId) {
        this.cartItemRepository.deleteByUserId(userId);
    }
}
