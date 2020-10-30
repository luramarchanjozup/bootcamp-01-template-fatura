package com.marcoscoutozup.fatura.cartao;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class CartaoService {

    private final EntityManager entityManager;

    public CartaoService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Cartao verificarExistenciaDeCartao(CartaoResponseListener cartaoResponseListener){
        Assert.notNull(cartaoResponseListener, "Os dados do cartão não podem ser nulos");
        List<Cartao> respostaCartao = entityManager.createNamedQuery("findCartaoByNumero", Cartao.class)
                .setParameter("numeroDoCartao", cartaoResponseListener.getId())
                .getResultList();

        Cartao cartao;

        if(respostaCartao.isEmpty()){
            cartao = cartaoResponseListener.toCartao();
            entityManager.persist(cartao);
            return cartao;
        }

        cartao = respostaCartao.get(0);
        return cartao;
    }

}
