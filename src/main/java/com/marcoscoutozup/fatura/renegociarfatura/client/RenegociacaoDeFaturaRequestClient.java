package com.marcoscoutozup.fatura.renegociarfatura.client;

import com.marcoscoutozup.fatura.renegociarfatura.RenegociacaoDeFatura;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.UUID;

public class RenegociacaoDeFaturaRequestClient {

    private UUID identificadorDaFatura;
    private Integer quantidade;
    private BigDecimal valor;

    public RenegociacaoDeFaturaRequestClient(RenegociacaoDeFatura renegociacaoDeFatura) {
        Assert.notNull(renegociacaoDeFatura, "A requisição de negociação de fatura não pode ser construída a partir de uma renegociação de fatura nula");
        this.identificadorDaFatura = renegociacaoDeFatura.getFatura().getId();
        this.quantidade = renegociacaoDeFatura.getParcelas();
        this.valor = renegociacaoDeFatura.getValorDaParcela();
    }

    public UUID getIdentificadorDaFatura() {
        return identificadorDaFatura;
    }

    public void setIdentificadorDaFatura(UUID identificadorDaFatura) {
        this.identificadorDaFatura = identificadorDaFatura;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
