package com.marcoscoutozup.fatura.parcelarfatura.client;

import com.marcoscoutozup.fatura.parcelarfatura.enums.StatusParcelamento;

public class ParcelamentoDaFaturaResponseClient {

    private StatusParcelamento resultado;

    public StatusParcelamento getResultado() {
        return resultado;
    }

    public void setResultado(StatusParcelamento resultado) {
        this.resultado = resultado;
    }
}
