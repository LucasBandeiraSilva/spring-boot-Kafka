package com.lucasbandeira.icompras.pedidos.model;

import com.lucasbandeira.icompras.pedidos.model.enums.StatusPedidos;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "pedido")
@Data
@NoArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(name = "codigo_cliente", nullable = false)
    private Long codigoCliente;

    @Column(name = "data_pedido",nullable = false)
    private LocalDateTime dataPedido;

    @Column(name = "total", precision = 16,scale = 2)
    private BigDecimal total;

    @Column(name = "chave_pagamento")
    private String chavePagamento;

    @Column(name = "observacoes")
    private String observacoes;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusPedidos status;

    @Column(name = "codigo_rastreio")
    private String codigoRastreio;

    @Column(name = "url_nf")
    private String UrlNotaFiscal;

    @Transient
    private DadosPagamento dadosPagamento;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens;
}
