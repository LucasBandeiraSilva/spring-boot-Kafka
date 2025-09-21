package com.lucasbandeira.icompras.pedidos.model.repository;

import com.lucasbandeira.icompras.pedidos.model.ItemPedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
}
