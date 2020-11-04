package com.marcoscoutozup.fatura.alterarvencimentofatura;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class VencimentoDaFaturaRequest {

    @NotNull
    @Min(1)
    @Max(31)
    private Integer diaDeVencimento;

    public Integer getDiaDeVencimento() {
        return diaDeVencimento;
    }

    public void setDiaDeVencimento(Integer diaDeVencimento) {
        this.diaDeVencimento = diaDeVencimento;
    }
}
