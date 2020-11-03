package com.marcoscoutozup.fatura.renegociarfatura;

import com.marcoscoutozup.fatura.fatura.Fatura;
import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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

    @Deprecated
    public RenegociacaoDeFatura() {
    }

    public RenegociacaoDeFatura(@NotNull @Positive Integer parcelas, @NotNull @Positive BigDecimal valorDaParcela) {
        this.parcelas = parcelas;
        this.valorDaParcela = valorDaParcela;
    }

    public UUID getId() {
        return id;
    }

    public void associarFaturaComRenegociacao(Fatura fatura) {
        Assert.notNull(fatura, "A fatura para associação a renegociação não pode ser nula");
        this.fatura = fatura;
    }
}
