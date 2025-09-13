package com.lucasbandeira.icompras.produtos.controler;

import com.lucasbandeira.icompras.produtos.model.Produto;
import com.lucasbandeira.icompras.produtos.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/produtos")
@RequiredArgsConstructor
public class ProdutoController {

    private final ProdutoService service;

    @PostMapping
    public ResponseEntity<Produto> salvar(@RequestBody Produto produto){
        return ResponseEntity.ok(service.salvar(produto));
    }

    @GetMapping("/{id}")
    public  ResponseEntity<Produto> obterPorCodigo(@PathVariable Long id){
        return service
                .obterPorCodigo(id)
                .map(ResponseEntity::ok)
                .orElseGet(()-> ResponseEntity.notFound().build());
    }
}
