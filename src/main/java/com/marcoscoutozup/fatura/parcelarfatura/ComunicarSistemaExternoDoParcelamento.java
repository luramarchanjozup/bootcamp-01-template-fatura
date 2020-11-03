package com.marcoscoutozup.fatura.parcelarfatura;

import com.marcoscoutozup.fatura.parcelarfatura.client.ParcelamentoClient;
import com.marcoscoutozup.fatura.parcelarfatura.client.ParcelamentoDaFaturaRequestClient;
import com.marcoscoutozup.fatura.parcelarfatura.client.ParcelamentoDaFaturaResponseClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import java.util.UUID;

@Service
public class ComunicarSistemaExternoDoParcelamento {

    private final ParcelamentoClient parcelamentoClient; //1
    private final EntityManager entityManager;
    private final Logger log = LoggerFactory.getLogger(ComunicarSistemaExternoDoParcelamento.class);

    public ComunicarSistemaExternoDoParcelamento(ParcelamentoClient parcelamentoClient, EntityManager entityManager) {
        this.parcelamentoClient = parcelamentoClient;
        this.entityManager = entityManager;
    }
                                                                                        //2
    public void comunicarSistemaExternoSobreParcelamentoDeFatura(UUID numeroDoCartao, ParcelamentoDeFatura parcelamentoDeFatura){
        Assert.notNull(parcelamentoDeFatura, "Não é possível fazer a comunicação de parcelamento com parcelamento nulo");

        //3
        ParcelamentoDaFaturaRequestClient requestClient =
                new ParcelamentoDaFaturaRequestClient(parcelamentoDeFatura);

        //4
        ParcelamentoDaFaturaResponseClient responseClient = parcelamentoClient.comunicarParcelamentoDeFatura(numeroDoCartao, requestClient);
        parcelamentoDeFatura.mudarStatusDoParcelamento(responseClient.getResultado());

        entityManager.merge(parcelamentoDeFatura);

    }

}
