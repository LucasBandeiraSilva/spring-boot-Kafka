package com.lucasbandeira.icompras.pedidos.model.repository;

import com.lucasbandeira.icompras.pedidos.model.ItemPedido;
import com.lucasbandeira.icompras.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long> {
    List<ItemPedido> findByPedido( Pedido pedido );
}
