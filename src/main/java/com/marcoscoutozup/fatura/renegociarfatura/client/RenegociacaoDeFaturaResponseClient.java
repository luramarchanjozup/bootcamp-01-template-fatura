package com.marcoscoutozup.fatura.renegociarfatura.client;

import com.marcoscoutozup.fatura.renegociarfatura.enums.StatusDaRenegociacao;

public class RenegociacaoDeFaturaResponseClient {

    private StatusDaRenegociacao resultado;

    public StatusDaRenegociacao getResultado() {
        return resultado;
    }

    public void setResultado(StatusDaRenegociacao resultado) {
        this.resultado = resultado;
    }
}
