package com.lucasbandeira.icompras.pedidos.model.controller.dto;

public record RecebimentoCallbackPagamentoDTO(Long codigo, String chavePagamento, boolean status, String observacoes) {
}
