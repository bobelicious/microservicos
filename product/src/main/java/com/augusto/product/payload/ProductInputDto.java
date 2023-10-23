package com.augusto.product.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductInputDto {
    private String name;
    private String sku;
    private int quantity;
    private Float unityPrice;
    private BatchInputDto batch;
    private Long user;
}
