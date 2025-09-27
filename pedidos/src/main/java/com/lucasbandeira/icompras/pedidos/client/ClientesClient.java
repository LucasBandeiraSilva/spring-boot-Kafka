package com.lucasbandeira.icompras.pedidos.client;

import com.lucasbandeira.icompras.pedidos.client.representation.ClienteRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "clientes",url = "${icompras.pedidos.clients.clientes.url}")
public interface ClientesClient {

    @GetMapping("/{codigo}")
    public ResponseEntity <ClienteRepresentation> obterPorCodigo( @PathVariable Long codigo);
}
