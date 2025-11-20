package com.spring.ecom_app.model.service;

import com.spring.ecom_app.model.dto.CartItemRequest;
import com.spring.ecom_app.model.entity.CartItem;
import com.spring.ecom_app.model.entity.Product;
import com.spring.ecom_app.model.entity.User;
import com.spring.ecom_app.model.repository.CartItemRepository;
import com.spring.ecom_app.model.repository.ProductRepository;
import com.spring.ecom_app.model.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartItemService {

    private final ProductRepository productRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;

    public CartItemService(ProductRepository productRepository, CartItemRepository cartItemRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.cartItemRepository = cartItemRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Boolean addToCart(String userId, CartItemRequest cartItemRequest) {
        Optional<Product> op_product = this.productRepository.findById(cartItemRequest.getProduct_id());
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
        User user  = user_op.get();
        CartItem cartItem_db = this.cartItemRepository.findByUserAndProduct(user, product);
        if(cartItem_db != null) {
            cartItem_db.setQuantity(cartItem_db.getQuantity() + cartItemRequest.getQuantity());
            cartItem_db.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItem_db.getQuantity())));
            this.cartItemRepository.save(cartItem_db);
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setUser(user);
            cartItem.setProduct(product);
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(product.getPrice().multiply(BigDecimal.valueOf(cartItemRequest.getQuantity())));
            this.cartItemRepository.save(cartItem);
        }
        return true;
    }

    @Transactional
    public Boolean deleteIntemFromCart(String userId, Long productId) {
        Optional<User>user_op = this.userRepository.findById(Long.valueOf(userId));
        Optional<Product> product_op = this.productRepository.findById(productId);
        if(user_op.isPresent() && product_op.isPresent()) {
            this.cartItemRepository.deleteByUserAndProduct(user_op.get(), product_op.get());
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Transactional(readOnly = true)
    public List<CartItem> getCart(String userId) {
       return this.userRepository.findById(Long.valueOf(userId)).map(this.cartItemRepository::findByUser).orElseGet(() -> List.of());
    }

    public void cleanCart(String userId) {
        this.userRepository.findById(Long.valueOf(userId)).ifPresent((user -> this.cartItemRepository.deleteByUser(user)));
    }
}
