package com.marcoscoutozup.fatura.fatura;

import com.marcoscoutozup.fatura.transacao.TransacaoResponse;

import java.math.BigDecimal;
import java.util.Set;

public class FaturaResponse {

    private Integer mes;
    private Integer ano;
    private Set<TransacaoResponse> transacoes;
    private BigDecimal totalDaFatura;

    public FaturaResponse(Integer mes, Integer ano, Set<TransacaoResponse> transacoes, BigDecimal totalDaFatura) {
        this.mes = mes;
        this.ano = ano;
        this.transacoes = transacoes;
        this.totalDaFatura = totalDaFatura;
    }

    public Integer getMes() {
        return mes;
    }

    public Integer getAno() {
        return ano;
    }

    public Set<TransacaoResponse> getTransacoes() {
        return transacoes;
    }

    public BigDecimal getTotalDaFatura() {
        return totalDaFatura;
    }
}
