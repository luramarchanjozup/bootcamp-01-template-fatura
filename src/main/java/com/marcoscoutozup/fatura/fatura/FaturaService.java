package com.marcoscoutozup.fatura.fatura;

import com.marcoscoutozup.fatura.cartao.Cartao;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class FaturaService {

    private final EntityManager entityManager;

    public FaturaService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Fatura verificarExistenciaDeFatura(Cartao cartao, Integer mesDaTransacao){
        Assert.notNull(cartao, "O cartão não pode ser nulo");
        Assert.notNull(mesDaTransacao, "O mês de transação não pode ser nulo");
        List<Fatura> respostaFatura = entityManager.createNamedQuery("findFaturaByCartaoAndMesCorrente", Fatura.class)
                .setParameter("numeroDoCartao", cartao.getNumeroDoCartao())
                .setParameter("mesCorrespondente", mesDaTransacao)
                .getResultList();

        Fatura fatura;

        if(respostaFatura.isEmpty()){
            fatura = new Fatura(mesDaTransacao, cartao);
            entityManager.persist(fatura);
            return fatura;
        }

        fatura = respostaFatura.get(0);
        return fatura;
    }
}
