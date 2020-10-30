package com.marcoscoutozup.fatura.listener;

import com.marcoscoutozup.fatura.transacao.TransacaoResponseListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
public class TransacaoListener {

    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    public void ouvirTransacoesDaMensageria(TransacaoResponseListener transacaoResponse) {
        Assert.notNull(transacaoResponse, "A resposta de transação não pode ser nula");
        System.out.println(transacaoResponse.toString());
    }
}
