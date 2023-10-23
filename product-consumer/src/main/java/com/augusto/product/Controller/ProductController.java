package com.augusto.product.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.augusto.product.payload.ItemsInputDto;
import com.augusto.product.payload.OrderDto;
import com.augusto.product.payload.ProductDto;
import com.augusto.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/product-service/system/products")
@Tag(name = "Product System API")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/{id}")
    @Operation(description = "Get Product by ID")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        return new ResponseEntity<ProductDto>(productService.getProductDtoById(id), HttpStatus.OK);
    }

    @Operation(description = "Get Product from ID user")
    @GetMapping("/user/{id}")
    public ResponseEntity<List<ProductDto>> getProductByUserId(@PathVariable Long id) {
        return new ResponseEntity<List<ProductDto>>(productService.getProductDtoByUserId(id),
                HttpStatus.OK);
    }

    @PostMapping("/order/new")
    @Operation(description = "Buy product")
    public ResponseEntity<OrderDto> buyProduct(@RequestBody List<ItemsInputDto> ItemsInputDto) {
        return new ResponseEntity<OrderDto>(productService.buyProduct(ItemsInputDto),
                HttpStatus.OK);
    }

    @PutMapping("/order/confirm-payment/{orderID}")
    @Operation(description = "Confirm payment")
    public ResponseEntity<OrderDto> confirmPayment(@PathVariable Long orderID) {
        return new ResponseEntity<OrderDto>(productService.confirmPayment(orderID), HttpStatus.OK);
    }

    @PutMapping("/order/cancel/{orderID}")
    @Operation(description = "Cancel order")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderID) {

        productService.CancelOrder(orderID);
        return new ResponseEntity<String>("Pedido cancelado com sucesso", HttpStatus.OK);

    }
}
