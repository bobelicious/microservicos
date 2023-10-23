package com.augusto.product.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemsInputDto {
    private String productSku;
    private int quantity;
    private int discount;
    private Long user;
}
