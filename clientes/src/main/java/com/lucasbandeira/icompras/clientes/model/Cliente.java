package com.lucasbandeira.icompras.clientes.model;

import jakarta.persistence.*;
import lombok.Data;


/**
 * codigo serial not null primary key,
 * nome varchar(150) not null,
 * cpf char(11) not null,
 * logradouro varchar(100),
 * numero varchar(10),
 * bairro varchar(100),
 * email varchar(150),
 * telefone varchar(20)
 */
@Entity
@Data
@Table(name = "clientes")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    @Column(length = 150,nullable = false)
    private String nome;

    @Column(length = 11, nullable = false)
    private String cpf;

    @Column(length = 100)
    private String logradouro;

    @Column(length = 10)
    private String numero;

    @Column(length = 100)
    private String bairro;

    @Column(length = 150)
    private String email;

    @Column(length = 20)
    private String telefone;


}
