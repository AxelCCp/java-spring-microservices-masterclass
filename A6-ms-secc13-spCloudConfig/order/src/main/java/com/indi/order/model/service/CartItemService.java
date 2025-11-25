package com.indi.order.model.service;

import com.indi.order.model.dto.CartItemRequest;
import com.indi.order.model.entity.CartItem;
import com.indi.order.model.repository.CartItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;


@Service
public class CartItemService {

    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional
    public Boolean addToCart(String userId, CartItemRequest cartItemRequest) {
        /*Optional<Product> op_product = this.productRepository.findById(cartItemRequest.getProduct_id());
        if(op_product.isEmpty()) {
            return Boolean.FALSE;
        }
        Product product = op_product.get();
        if(product.getStockQuantity() < cartItemRequest.getQuantity()) {
            return Boolean.FALSE;
        }
        Optional<User>user_op = this.userRepository.findById(Long.valueOf(userId));
        if(user_op.isEmpty()) {
            return Boolean.FALSE;
        }
        User user  = user_op.get();*/
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
