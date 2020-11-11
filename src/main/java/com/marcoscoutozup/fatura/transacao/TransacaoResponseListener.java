package com.marcoscoutozup.fatura.transacao;

import com.marcoscoutozup.fatura.cartao.CartaoResponseListener;
import com.marcoscoutozup.fatura.estabelecimento.EstabelecimentoResponseListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransacaoResponseListener {

    private UUID id;
    private BigDecimal valor;
    private EstabelecimentoResponseListener estabelecimento;
    private CartaoResponseListener cartao;
    private LocalDateTime efetivadaEm;

    public Transacao toTransacao(){
        return new Transacao(this.id, this.valor, this.estabelecimento.toEstabelecimento(), this.efetivadaEm);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public EstabelecimentoResponseListener getEstabelecimento() {
        return estabelecimento;
    }

    public void setEstabelecimento(EstabelecimentoResponseListener estabelecimento) {
        this.estabelecimento = estabelecimento;
    }

    public CartaoResponseListener getCartao() {
        return cartao;
    }

    public void setCartao(CartaoResponseListener cartao) {
        this.cartao = cartao;
    }

    public LocalDateTime getEfetivadaEm() {
        return efetivadaEm;
    }

    public void setEfetivadaEm(LocalDateTime efetivadaEm) {
        this.efetivadaEm = efetivadaEm;
    }

}
