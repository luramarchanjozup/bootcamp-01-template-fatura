package com.marcoscoutozup.fatura.cartaovirtual;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class CartaoVirtualRequest {

    @NotNull
    @Positive
    private BigDecimal limite;

    public CartaoVirtual toCartaoVirtual(){
        return new CartaoVirtual(this.limite);
    }

    public BigDecimal getLimite() {
        return limite;
    }

    public void setLimite(BigDecimal limite) {
        this.limite = limite;
    }
}
