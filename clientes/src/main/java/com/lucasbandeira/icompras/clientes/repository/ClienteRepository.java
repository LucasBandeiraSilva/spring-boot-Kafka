package com.lucasbandeira.icompras.clientes.repository;

import com.lucasbandeira.icompras.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,Long> {
}
