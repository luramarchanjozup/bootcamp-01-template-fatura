package com.marcoscoutozup.fatura.cartao.saldo;

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
