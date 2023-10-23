package com.augusto.address.payload;

import com.augusto.address.model.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDto {
    private String cep;
    private String numero;
    private String rua;
    private String complemento;
    private String bairro;
    private String cidade;
    private String estado;
    private Long userId;

    public AddressDto(Address address) {
        this.cep = address.getCep();
        this.numero = address.getNumero();
        this.rua = address.getRua();
        this.complemento = address.getComplemento();
        this.bairro = address.getBairro();
        this.cidade = address.getCidade();
        this.estado = address.getEstado();
        this.userId = address.getUserId();
    }
}
