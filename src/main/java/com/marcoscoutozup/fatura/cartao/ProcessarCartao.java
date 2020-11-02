package com.marcoscoutozup.fatura.cartao;

import com.marcoscoutozup.fatura.fatura.parcelarfatura.ParcelarFaturaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProcessarCartao {

    private final EntityManager entityManager;

    private final Logger log = LoggerFactory.getLogger(ProcessarCartao.class);

    public ProcessarCartao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional                                  //1
    public Cartao verificarExistenciaDeCartao(CartaoResponseListener cartaoResponseListener){
        Assert.notNull(cartaoResponseListener, "Os dados do cartão não podem ser nulos");
                //2
        List<Cartao> respostaCartao = entityManager.createNamedQuery("findCartaoByNumero", Cartao.class)
                .setParameter("numeroDoCartao", cartaoResponseListener.getId())
                .getResultList();

        Cartao cartao;

        //3
        if(respostaCartao.isEmpty()){
            cartao = cartaoResponseListener.toCartao();
            entityManager.persist(cartao);
            log.info("Cartão não foi encontrado no sistema, salvando informações do cartão, Número do cartão: {}", cartao.getNumeroDoCartao());
            return cartao;
        }

        cartao = respostaCartao.get(0);
        return cartao;
    }

}
