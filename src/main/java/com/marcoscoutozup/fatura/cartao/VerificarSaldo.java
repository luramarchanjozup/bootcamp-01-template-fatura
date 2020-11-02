package com.marcoscoutozup.fatura.cartao;

import com.marcoscoutozup.fatura.fatura.Fatura;
import com.marcoscoutozup.fatura.transacao.Transacao;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@Service
public class VerificarSaldo {

    private final EntityManager entityManager;

    public VerificarSaldo(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public SaldoResponse verificarSaldoDisponivelNoCartao(UUID numeroDoCartao, BigDecimal limite){
        Assert.notNull(numeroDoCartao, "O numero do cartão não pode ser nulo para calculo de saldo");
        Assert.notNull(limite, "O limite do cartão não pode ser nulo para calculo de saldo");

                    //1
        final List<Fatura> faturas = entityManager.createNamedQuery("findFaturaByCartaoAndMesCorrente", Fatura.class)
                .setParameter("numeroDoCartao", numeroDoCartao)
                .setParameter("mesCorrespondente", LocalDate.now().getMonth().getValue())
                .getResultList();

        //2
        if(faturas.isEmpty()){
            return new SaldoResponse(limite, new HashSet<>());
        }

        Fatura fatura = faturas.get(0);

        //3
        SaldoResponse saldoResponse =                                       //4
                new SaldoResponse(fatura.calcularSaldoDoCartao(limite), Transacao.toResponseSet(fatura.retornarAsUltimas10Transacoes()));

        return saldoResponse;
    }
}
