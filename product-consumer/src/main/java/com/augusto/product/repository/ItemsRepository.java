package com.augusto.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.augusto.product.model.Items;

public interface ItemsRepository extends JpaRepository<Items,Long> {
    
}
