package com.lucasbandeira.icompras.pedidos.publisher;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasbandeira.icompras.pedidos.model.Pedido;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PagamentoPublisher {

    private final DetalhePedidoMapper detalhePedidoMapper;
    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String,String> kafkaTemplate;

    @Value("${icompras.config.kafka.topics.pedidos-pagos}")
    private String topico;


    public void publicar( Pedido pedido ){
        log.info("Publicando pedido pago {}",pedido.getCodigo());

        try{
            var representation = detalhePedidoMapper.map(pedido);
            var json = objectMapper.writeValueAsString(representation);
            kafkaTemplate.send(topico,"dados",json);
        } catch (JsonProcessingException e) {
                log.error("Erro ao processar o JSON", e);
        }catch (RuntimeException e){
            log.error("Erro técnico ao públicar no tópico de pedidos",e);
        }

    }
}
