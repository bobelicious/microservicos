package com.augusto.product.model;

import java.util.List;
import com.augusto.product.enums.PaymentStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pedidos")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "id_usuario", nullable = false)
    private Long user;
    @Column(name = "preco_total", nullable = false)  
    private Float totalPrice = 0F;
    @Column(name = "preco_final", nullable = false)
    private Float finalPrice = 0F;
    @Column(name = "desconto")
    private Float discount;
    @OneToMany(orphanRemoval = true, mappedBy = "order")
    private List<Items> items;
    @Enumerated(EnumType.STRING)
    @Column(name = "status_pagamento")
    private PaymentStatus payment;
}