package com.augusto.user.proxy;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import com.augusto.user.payload.ProductDto;

@FeignClient(name = "product-service")
public interface ProductProxy {
    @GetMapping("/api/v1/product-service/process/products/{id}")
    public ProductDto getProductDtoById(@PathVariable Long id,
            @RequestHeader(name = "Authorization") String token);

    @GetMapping("/api/v1/product-service/process/products/user/{id}")
    public List<ProductDto> getProductByUserId(@PathVariable Long id,
            @RequestHeader(name = "Authorization") String token);
}