package com.augusto.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.augusto.product.model.Batch;

public interface BatchRepository extends JpaRepository<Batch,Long> {
    
}
