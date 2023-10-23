package com.augusto.address.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDto {
    private Long id;
    private String cep;
    private String numero;
    private String rua;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private Long userId;
}
