package com.lucasbandeira.icompras.pedidos.model.repository;

import com.lucasbandeira.icompras.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoRepository extends JpaRepository<Pedido,Long> {
}
