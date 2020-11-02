package com.marcoscoutozup.fatura.fatura.parcelarfatura;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class ParcelamentoDeFatura {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotNull
    @Positive
    private Integer parcelas;

    @NotNull
    @Positive
    private BigDecimal valorDaParcela;

    @Deprecated
    public ParcelamentoDeFatura() {
    }

    public ParcelamentoDeFatura(@NotNull @Positive Integer parcelas, @NotNull @Positive BigDecimal valorDaParcela) {
        this.parcelas = parcelas;
        this.valorDaParcela = valorDaParcela;
    }

    public UUID getId() {
        return id;
    }
}
