package com.lucasbandeira.icompras.produtos.repository;

import com.lucasbandeira.icompras.produtos.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
}
