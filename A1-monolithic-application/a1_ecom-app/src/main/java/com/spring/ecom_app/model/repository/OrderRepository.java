package com.spring.ecom_app.model.repository;

import com.spring.ecom_app.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository  extends JpaRepository<Order, Long> { }
