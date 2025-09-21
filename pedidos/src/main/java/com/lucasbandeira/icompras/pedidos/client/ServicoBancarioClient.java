package com.lucasbandeira.icompras.pedidos.client;

import com.lucasbandeira.icompras.pedidos.model.Pedido;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class ServicoBancarioClient {

    public String solicitarPagamento( Pedido pedido ){
        log.info("Solicitando pagamento para o pedido de codigo: {}.",pedido.getCodigo());
        return UUID.randomUUID().toString();
    }
}
