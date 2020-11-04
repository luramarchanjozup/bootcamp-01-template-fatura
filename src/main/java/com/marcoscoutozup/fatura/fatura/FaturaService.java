package com.marcoscoutozup.fatura.fatura;

import com.marcoscoutozup.fatura.cartao.Cartao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class FaturaService {

    private final EntityManager entityManager;

    private final Logger log = LoggerFactory.getLogger(FaturaService.class);

    public FaturaService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    public Fatura verificarExistenciaDeFatura(Cartao cartao, Integer mesDaTransacao, Integer anoDaTransacao){
        Assert.notNull(cartao, "O cartão não pode ser nulo");
        Assert.notNull(mesDaTransacao, "O mês de transação não pode ser nulo");
        Assert.notNull(anoDaTransacao, "O ano de transação não pode ser nulo");

        log.info("[PROCESSAMENTO DE FATURA] Processando fatura do mês {}, ano {} para o cartão {}", mesDaTransacao, anoDaTransacao, cartao.getNumeroDoCartao());

        List<Fatura> respostaFatura = entityManager.createNamedQuery("findFaturaByCartaoAndData", Fatura.class)
                .setParameter("numeroDoCartao", cartao.getNumeroDoCartao())
                .setParameter("mes", mesDaTransacao)
                .setParameter("ano", anoDaTransacao)
                .getResultList();

        Fatura fatura;

        if(respostaFatura.isEmpty()){
            fatura = new Fatura(mesDaTransacao, anoDaTransacao, cartao);
            entityManager.persist(fatura);
            log.info("[PROCESSAMENTO DE FATURA] Fatura não foi encontrada no sistema, salvando informações da fatura. Id: {}", fatura.getId());
            return fatura;
        }

        fatura = respostaFatura.get(0);
        return fatura;
    }
}
