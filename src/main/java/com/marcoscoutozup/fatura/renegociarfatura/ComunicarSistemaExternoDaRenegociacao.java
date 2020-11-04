package com.marcoscoutozup.fatura.renegociarfatura;

import com.marcoscoutozup.fatura.renegociarfatura.client.RenegociacaoDeFaturaClient;
import com.marcoscoutozup.fatura.renegociarfatura.client.RenegociacaoDeFaturaRequestClient;
import com.marcoscoutozup.fatura.renegociarfatura.client.RenegociacaoDeFaturaResponseClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.util.UUID;

@Service
public class ComunicarSistemaExternoDaRenegociacao {

    private final RenegociacaoDeFaturaClient renegociacaoDeFaturaClient; //1
    private final EntityManager entityManager;

    public ComunicarSistemaExternoDaRenegociacao(RenegociacaoDeFaturaClient renegociacaoDeFaturaClient, EntityManager entityManager) {
        this.renegociacaoDeFaturaClient = renegociacaoDeFaturaClient;
        this.entityManager = entityManager;
    }

                                                                                            //2
    public void comunicarSistemaExternoSobreParcelamentoDeFatura(UUID numeroDoCartao, RenegociacaoDeFatura renegociacaoDeFatura){
        Assert.notNull(renegociacaoDeFatura, "Não é possível fazer a comunicação de parcelamento com parcelamento nulo");

        //3
        final RenegociacaoDeFaturaRequestClient request =
                new RenegociacaoDeFaturaRequestClient(renegociacaoDeFatura);

        //4
        final RenegociacaoDeFaturaResponseClient responseClient =
                renegociacaoDeFaturaClient.comunicarRenegociacaoDeFatura(numeroDoCartao, request);

        renegociacaoDeFatura.mudarStatusDaRenegociacao(responseClient.getResultado());
        entityManager.merge(renegociacaoDeFatura);
    }
}
