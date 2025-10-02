package com.lucasbandeira.icompras.pedidos.model.controller;

import com.lucasbandeira.icompras.pedidos.model.controller.dto.RecebimentoCallbackPagamentoDTO;
import com.lucasbandeira.icompras.pedidos.model.service.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pedidos/callback-pagamentos")
@RequiredArgsConstructor
public class RecebimentoCallbackPagamentoController {

    private final PedidoService pedidoService;

    @PostMapping
    public ResponseEntity<Object>atualizarStatusPagamento( @RequestBody RecebimentoCallbackPagamentoDTO body, @RequestHeader(required = true,name = "apiKey")String apiKey ){

        pedidoService.atualizarStatusPagamento(body.codigo(),body.chavePagamento(),body.status(),body.observacoes());

        return ResponseEntity.ok().build();
    }
}
