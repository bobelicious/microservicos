package com.augusto.product.payload;

import java.util.List;
import com.augusto.product.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDto {
    private Long id;
    private Long user;
    private Float totalPrice;
    private Float finalPrice;
    private Float discount;
    private List<ItemsDto> items;
    private PaymentStatus payment;
}
