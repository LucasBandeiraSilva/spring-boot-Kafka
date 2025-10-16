package com.lucasbandeira.icompras.logistica.subscriber;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucasbandeira.icompras.logistica.subscriber.representation.AtualizacaoFaturamentoRepresentation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class FaturamentoSubscriber {

    private final ObjectMapper objectMapper;

    @KafkaListener(groupId = "${spring.kafka.consumer.group-id}",topics = "${icompras.config.kafka.topics.pedidos-faturados}")
    public void listen(String json){
        log.info("Recebendo pedido para envio: {} ",json);

        try {
            var representation = objectMapper.readValue(json, AtualizacaoFaturamentoRepresentation.class);
        } catch (Exception e) {
            log.error("Erro ao preparar pedido para envio: ",e.getMessage());
        }
    }
}
