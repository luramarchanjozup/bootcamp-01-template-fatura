package com.marcoscoutozup.fatura.listener;

import com.marcoscoutozup.fatura.cartao.Cartao;
import com.marcoscoutozup.fatura.cartao.ProcessarCartao;
import com.marcoscoutozup.fatura.fatura.Fatura;
import com.marcoscoutozup.fatura.fatura.FaturaService;
import com.marcoscoutozup.fatura.transacao.Transacao;
import com.marcoscoutozup.fatura.transacao.TransacaoResponseListener;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Component
public class TransacaoListener {

    private final EntityManager entityManager;
    private final ProcessarCartao processarCartao; //1
    private final FaturaService faturaService; //2

    public TransacaoListener(EntityManager entityManager, ProcessarCartao processarCartao, FaturaService faturaService) {
        this.entityManager = entityManager;
        this.processarCartao = processarCartao;
        this.faturaService = faturaService;
    }

    @Transactional
    @KafkaListener(topics = "${spring.kafka.topic.transactions}")
    public void ouvirTransacoesDaMensageria(TransacaoResponseListener transacaoResponse) {
        Assert.notNull(transacaoResponse, "A resposta de transação não pode ser nula");

        //3
        Transacao transacao = transacaoResponse.toTransacao();
        entityManager.persist(transacao);

        //4
        Cartao cartao = processarCartao.verificarExistenciaDeCartao(transacaoResponse.getCartao());

        //5
        Fatura fatura = faturaService.verificarExistenciaDeFatura(cartao, transacao.retornarMesDaTransacao());
        fatura.adicionarTransacaoNaFatura(transacao);
        entityManager.merge(fatura);
    }

}
