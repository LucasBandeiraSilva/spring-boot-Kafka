package com.lucasbandeira.icompras.clientes.controller;

import com.lucasbandeira.icompras.clientes.model.Cliente;
import com.lucasbandeira.icompras.clientes.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<Cliente> salvar( @RequestBody Cliente cliente ){
        return ResponseEntity.ok(service.salvar(cliente));
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<Cliente> obterPorCodigo(@PathVariable Long codigo){
        return service
                .obterPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> deletar(@PathVariable Long codigo){
        var cliente = service.obterPorCodigo(codigo).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente Inexistente"));
        service.deletar(cliente);
        return ResponseEntity.noContent().build();
    }
}
