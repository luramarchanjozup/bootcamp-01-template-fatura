package com.marcoscoutozup.fatura.saldocartao;

import java.math.BigDecimal;

public class LimiteResponse {

    private BigDecimal limite;

    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }
}
