package com.marcoscoutozup.fatura.renegociarfatura;

import com.marcoscoutozup.fatura.fatura.Fatura;
import com.marcoscoutozup.fatura.renegociarfatura.enums.StatusDaRenegociacao;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class RenegociacaoDeFatura {

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
    private StatusDaRenegociacao statusDaRenegociacao;

    @Deprecated
    public RenegociacaoDeFatura() {
    }

    public RenegociacaoDeFatura(@NotNull @Positive Integer parcelas, @NotNull @Positive BigDecimal valorDaParcela) {
        this.parcelas = parcelas;
        this.valorDaParcela = valorDaParcela;
        this.statusDaRenegociacao = StatusDaRenegociacao.PENDENTE;
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

    public StatusDaRenegociacao getStatusDaRenegociacao() {
        return statusDaRenegociacao;
    }

    public void associarFaturaComRenegociacao(Fatura fatura) {
        Assert.notNull(fatura, "A fatura para associação a renegociação não pode ser nula");
        this.fatura = fatura;
    }

    public void mudarStatusDaRenegociacao(StatusDaRenegociacao statusDaRenegociacao) {
        Assert.notNull(statusDaRenegociacao, "O status da renegociação não pode ser nulo para ser modificado");
        this.statusDaRenegociacao = statusDaRenegociacao;
    }
}
