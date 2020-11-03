package com.marcoscoutozup.fatura.renegociarfatura;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class RenegociacaoDeFaturaRequest {

    @NotNull
    @Positive
    private Integer parcelas;

    @NotNull
    @Positive
    private BigDecimal valorDaParcela;

    public RenegociacaoDeFatura toRenegociacaoDeFatura(){
        return new RenegociacaoDeFatura(this.parcelas, this.valorDaParcela);
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public void setParcelas(Integer parcelas) {
        this.parcelas = parcelas;
    }

    public BigDecimal getValorDaParcela() {
        return valorDaParcela;
    }

    public void setValorDaParcela(BigDecimal valorDaParcela) {
        this.valorDaParcela = valorDaParcela;
    }

}
