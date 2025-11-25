package com.indi.product.model.repository;

import com.indi.product.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    public List<Product> findByActiveTrue();

    @Query("select p from Product p where p.active = true and p.stockQuantity > 0 and lower(p.name) like lower(concat('%', :keyword, '%'))")
    public List<Product> searchProducts(String keyword);
}
