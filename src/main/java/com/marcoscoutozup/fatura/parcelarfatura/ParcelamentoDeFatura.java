package com.marcoscoutozup.fatura.parcelarfatura;

import com.marcoscoutozup.fatura.fatura.Fatura;
import com.marcoscoutozup.fatura.parcelarfatura.enums.StatusParcelamento;
import org.springframework.util.Assert;

import javax.persistence.*;
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

    @OneToOne
    @NotNull
    private Fatura fatura;

    @Enumerated(EnumType.STRING)
    private StatusParcelamento statusParcelamento;

    @Deprecated
    public ParcelamentoDeFatura() {
    }

    public ParcelamentoDeFatura(@NotNull @Positive Integer parcelas, @NotNull @Positive BigDecimal valorDaParcela) {
        this.parcelas = parcelas;
        this.valorDaParcela = valorDaParcela;
        this.statusParcelamento = StatusParcelamento.PENDENTE;
    }

    public UUID getId() {
        return id;
    }

    public Integer getParcelas() {
        return parcelas;
    }

    public BigDecimal getValorDaParcela() {
        return valorDaParcela;
    }

    public Fatura getFatura() {
        return fatura;
    }

    public void relacionarFaturaAoParcelamento(Fatura fatura){
        Assert.notNull(fatura, "Fatura não pode ser nula para associação ao parcelamento");
        this.fatura = fatura;
    }

    public void mudarStatusDoParcelamento(StatusParcelamento statusParcelamento){
        Assert.notNull(statusParcelamento, "Não é possível modificar o status de parcelamento se for nulo");
        this.statusParcelamento = statusParcelamento;
    }
}
