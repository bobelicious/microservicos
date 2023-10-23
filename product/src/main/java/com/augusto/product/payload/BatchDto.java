package com.augusto.product.payload;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatchDto {
    private String date;
    @JsonIgnore
    private ProductDto product;
    private Float price;
    private String validity;
    private String batchNum;
}
