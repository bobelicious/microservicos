package com.augusto.product.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.augusto.product.model.Product;

public interface ProductRepository  extends JpaRepository<Product,Long>{
    Optional <List<Product>> findProductByUser(Long id);
    Optional<List<Product>> findAllBySkuIn(List<String> sku);
    
}