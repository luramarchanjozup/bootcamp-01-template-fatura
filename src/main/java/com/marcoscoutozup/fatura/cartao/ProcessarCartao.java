package com.marcoscoutozup.fatura.cartao;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class ProcessarCartao {

    private final EntityManager entityManager;

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
            return cartao;
        }

        cartao = respostaCartao.get(0);
        return cartao;
    }

}
