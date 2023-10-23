package com.augusto.product.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BatchInputDto {
    private String date;
    private Long product;
    private Float price;
    private String validity;
    private String batchNum;
}
