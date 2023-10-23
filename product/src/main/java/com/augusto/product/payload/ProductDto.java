package com.augusto.product.payload;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private String name;
    private String environment;
    private Long user;
    private String sku;
    private int quantity;
    private List<BatchDto> batch;
    private Float unityPrice;
}
