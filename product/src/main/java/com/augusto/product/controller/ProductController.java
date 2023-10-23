package com.augusto.product.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.augusto.product.payload.BatchInputDto;
import com.augusto.product.payload.ItemsInputDto;
import com.augusto.product.payload.OrderDto;
import com.augusto.product.payload.ProductDto;
import com.augusto.product.payload.ProductInputDto;
import com.augusto.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Product Service API")
@RestController
@RequestMapping("/api/v1/product-service/process/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(description = "Get Product from ID user")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ProductDto>> getProductByUserId(@PathVariable Long id,
            @RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<List<ProductDto>>(productService.getProductDtoByUserId(id, token),
                HttpStatus.OK);
    }

    @Operation(description = "Create a  Product")
    @PostMapping("/new")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductInputDto productInputDto,
            @RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<ProductDto>(
                productService.createProductDto(productInputDto, token), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id,
            @RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<ProductDto>(productService.getProductDtoById(id, token),
                HttpStatus.OK);
    }

    @Operation(description = "Insert a new batch in product")
    @PutMapping("/add-batch")
    public ResponseEntity<String> insertBatch(@RequestBody BatchInputDto batchInputDto) {
        productService.insertBatch(batchInputDto);
        return new ResponseEntity<String>("Updated", HttpStatus.OK);
    }

    @PostMapping("/order/new")
    @Operation(description = "Buy product")
    public ResponseEntity<OrderDto> buyProduct(@RequestBody List<ItemsInputDto> ItemsInputDto,
            @RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<OrderDto>(productService.buyProduct(ItemsInputDto, token),
                HttpStatus.OK);
    }

    @PutMapping("/order/confirm-payment/{orderID}")
    @Operation(description = "Confirm payment")
    public ResponseEntity<OrderDto> confirmPayment(@PathVariable Long orderID,
            @RequestHeader(name = "Authorization") String token) {
        return new ResponseEntity<OrderDto>(productService.confirmPayment(orderID, token),
                HttpStatus.OK);
    }

    @PutMapping("/order/cancel/{orderID}")
    @Operation(description = "Cancel order")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderID,
            @RequestHeader(name = "Authorization") String token) {
        productService.CancelOrder(orderID, token);
        return new ResponseEntity<String>("Pedido cancelado com sucesso", HttpStatus.OK);
    }
}
