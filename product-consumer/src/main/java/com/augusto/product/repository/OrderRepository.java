package com.augusto.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.augusto.product.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long>{
    
}
