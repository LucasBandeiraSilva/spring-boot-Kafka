package com.lucasbandeira.icompras.pedidos.service;

import com.lucasbandeira.icompras.pedidos.model.enums.StatusPedido;
import com.lucasbandeira.icompras.pedidos.model.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizacaoStatusPedidoService {

    private final PedidoRepository repository;

    @Transactional
    public void atualizarStatus( Long codigo, StatusPedido statusPedido,String urlNotaFiscal ,String codigoRastreio ){
        repository.findById(codigo).ifPresent(pedido -> {
            pedido.setStatus(statusPedido);
            pedido.setUrlNotaFiscal(urlNotaFiscal);
            pedido.setCodigoRastreio(codigoRastreio);
        });
    }
}
