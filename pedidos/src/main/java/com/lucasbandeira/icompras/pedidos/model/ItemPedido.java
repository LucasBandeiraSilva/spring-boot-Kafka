package com.lucasbandeira.icompras.pedidos.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@Table(name = "item_pedido")
public class ItemPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @JoinColumn(name = "codigo_Pedido")
    @ManyToOne
    private Pedido pedido;

    @Column(name = "codigo_produto")
    private Long codigoProduto;

    @Column(name = "quantidade")
    private Integer quantidade;

    @Column(name = "valor_unitario",precision = 16,scale = 2)
    private BigDecimal valorUnitario;

    @Transient
    private String nome;


}
