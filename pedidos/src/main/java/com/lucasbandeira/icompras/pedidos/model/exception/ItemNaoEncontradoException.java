package com.lucasbandeira.icompras.pedidos.model.exception;

public class ItemNaoEncontradoException extends RuntimeException {
    public ItemNaoEncontradoException( String message ) {
        super(message);
    }
}
