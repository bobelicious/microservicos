package com.augusto.product.model;

import java.util.List;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "produto_revisao")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome")
    private String name;
    @Column(name = "id_usuario", nullable = false)
    private Long user;
    @Column(name = "codigo_sku", nullable = false)
    private String sku;
    @Column(name = "quantidade", nullable = false)
    private int quantity;
    @OneToMany(orphanRemoval = true, mappedBy = "product")
    private List<Batch> batch;
    @Column(name = "preco_unitario", nullable = false)
    private Float unityPrice;
    @OneToMany(mappedBy = "product")
    private List<Items> pedidoItems;
}
