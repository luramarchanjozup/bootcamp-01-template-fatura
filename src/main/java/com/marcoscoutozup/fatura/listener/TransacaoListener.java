package com.marcoscoutozup.fatura.listener;

import com.marcoscoutozup.fatura.cartao.Cartao;
import com.marcoscoutozup.fatura.cartao.CartaoService;
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
    private final CartaoService cartaoService; //1
    private final FaturaService faturaService; //2

    public TransacaoListener(EntityManager entityManager, CartaoService cartaoService, FaturaService faturaService) {
        this.entityManager = entityManager;
        this.cartaoService = cartaoService;
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
        Cartao cartao = cartaoService.verificarExistenciaDeCartao(transacaoResponse.getCartao());

        //5
        Fatura fatura = faturaService.verificarExistenciaDeFatura(cartao, transacao.retornarMesDaTransacao());
        fatura.adicionarTransacaoNaFatura(transacao);
        entityManager.merge(fatura);
    }

}
