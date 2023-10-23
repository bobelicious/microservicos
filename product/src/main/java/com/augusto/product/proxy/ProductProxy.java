package com.augusto.product.proxy;

import java.util.List;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import com.augusto.product.payload.ItemsInputDto;
import com.augusto.product.payload.OrderDto;
import com.augusto.product.payload.ProductDto;

@FeignClient(name = "product-service-consumer")
public interface ProductProxy {
    @GetMapping("/api/v1/product-service/system/products/{id}")
    public ProductDto getProductById(@PathVariable Long id,
            @RequestHeader(name = "Authorization") String token);

    @GetMapping("/api/v1/product-service/system/products/user/{id}")
    public List<ProductDto> getProductByUserId(@PathVariable Long id,
            @RequestHeader(name = "Authorization") String token);

    @PostMapping("/api/v1/product-service/system/products/order/new")
    public OrderDto buyProduct(@RequestBody List<ItemsInputDto> ItemsInputDto,
            @RequestHeader(name = "Authorization") String token);

    @PutMapping("/api/v1/product-service/system/products/order/confirm-payment/{orderID}")
    public OrderDto confirmPayment(@PathVariable Long orderID,
            @RequestHeader(name = "Authorization") String token);

    @PutMapping("/api/v1/product-service/system/products/order/cancel/{orderID}")
    public Object cancelOrder(@PathVariable Long orderID,
            @RequestHeader(name = "Authorization") String token);
}
