package com.lucasbandeira.icompras.pedidos.client;

import com.lucasbandeira.icompras.pedidos.client.representation.ProdutoRepresentation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "produtos",url = "${icompras.pedidos.produtos.url}")
public interface ProdutosClient {

    @GetMapping("/{id}")
    ResponseEntity <ProdutoRepresentation> obterPorCodigo( @PathVariable Long id);
}
