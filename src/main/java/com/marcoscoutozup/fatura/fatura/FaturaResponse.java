package com.marcoscoutozup.fatura.fatura;

import com.marcoscoutozup.fatura.transacao.TransacaoResponse;

import java.math.BigDecimal;
import java.util.Set;

public class FaturaResponse {

    private Integer mesCorrespondente;
    private Set<TransacaoResponse> transacoes;
    private BigDecimal totalDaFatura;

    public FaturaResponse(Integer mesCorrespondente, Set<TransacaoResponse> transacoes, BigDecimal totalDaFatura) {
        this.mesCorrespondente = mesCorrespondente;
        this.transacoes = transacoes;
        this.totalDaFatura = totalDaFatura;
    }

    public Integer getMesCorrespondente() {
        return mesCorrespondente;
    }

    public Set<TransacaoResponse> getTransacoes() {
        return transacoes;
    }

    public BigDecimal getTotalDaFatura() {
        return totalDaFatura;
    }
}
