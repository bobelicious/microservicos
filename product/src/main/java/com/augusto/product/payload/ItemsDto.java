package com.augusto.product.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ItemsDto {
    private ProductDto product;
    private int quantity;
    @JsonIgnore
    private OrderDto order;
    private Long user;
}
