package com.lucasbandeira.icompras.pedidos.controller;

import com.lucasbandeira.icompras.pedidos.model.ErroResposta;
import com.lucasbandeira.icompras.pedidos.model.Pedido;
import com.lucasbandeira.icompras.pedidos.controller.dto.AdicaoNovoPagamentoDTO;
import com.lucasbandeira.icompras.pedidos.controller.dto.NovoPedidoDTO;
import com.lucasbandeira.icompras.pedidos.controller.mappers.PedidoMapper;
import com.lucasbandeira.icompras.pedidos.model.exception.ItemNaoEncontradoException;
import com.lucasbandeira.icompras.pedidos.model.exception.ValidationException;
import com.lucasbandeira.icompras.pedidos.service.PedidoService;
import com.lucasbandeira.icompras.pedidos.publisher.DetalhePedidoMapper;
import com.lucasbandeira.icompras.pedidos.publisher.representation.DetalhePedidoRepresentation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping( "/pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;
    private final DetalhePedidoMapper detalhePedidoMapper;

    @PostMapping
    public ResponseEntity<Object> criar( @RequestBody NovoPedidoDTO novoPedidoDTO ){
        try {
            Pedido pedido = mapper.map(novoPedidoDTO);
            Pedido novoPedido = service.criarPedido(pedido);
            return ResponseEntity.ok(novoPedido.getCodigo());
        } catch (ValidationException e) {
            var erroResposta = new ErroResposta("Erro de validação",e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(erroResposta);
        }
    }

    @PostMapping("/pagamentos")
    public ResponseEntity<Object> adicionarNovoPagamento( @RequestBody AdicaoNovoPagamentoDTO dto ) {
        try {
            service.adicionarNovoPagamento(dto.codigoPedido(), dto.dadosCartao(), dto.tipoPagamento());
            return ResponseEntity.noContent().build();
        } catch (ItemNaoEncontradoException e) {
            var erro = new ErroResposta("Item Não encontrado","codigoPedido", e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<DetalhePedidoRepresentation> obterDetalhesPedido(@PathVariable Long codigo){
        return service.carregarDadosCompletosPedidos(codigo)
                .map(detalhePedidoMapper::map)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
