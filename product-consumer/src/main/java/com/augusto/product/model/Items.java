package com.augusto.product.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "itens_pedido")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Items {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "quantidade", nullable = false)
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "id_pedido")
    private Order order;

    @Column(name = "id_usuario", unique = false)
    private Long user;

    @Column(name = "desconto", nullable = true)
    private int discount;
}
