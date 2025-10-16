package com.lucasbandeira.icompras.produtos.service;

import com.lucasbandeira.icompras.produtos.model.Produto;
import com.lucasbandeira.icompras.produtos.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProdutoService {

    private final ProdutoRepository repository;


    public Produto salvar(Produto produto){
        return repository.save(produto);
    }

    public Optional<Produto> obterPorCodigo(Long id){
        return repository.findById(id);
    }

    public void deletar( Produto produto ) {
        produto.setAtivo(false);
        repository.save(produto);
    }
}
