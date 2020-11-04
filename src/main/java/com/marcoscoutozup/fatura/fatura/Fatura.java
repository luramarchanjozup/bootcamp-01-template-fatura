package com.marcoscoutozup.fatura.fatura;

import com.marcoscoutozup.fatura.cartao.Cartao;
import com.marcoscoutozup.fatura.transacao.Transacao;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@NamedQuery(name = "findFaturaByCartaoAndData",
        query = "select f from Fatura f where f.cartao.numeroDoCartao = :numeroDoCartao and mes = :mes and ano = :ano")
public class Fatura {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotNull
    @Positive
    private Integer mes;

    @NotNull
    @Positive
    private Integer ano;

    @OneToMany
    private Set<Transacao> transacoes;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public Fatura() {
    }

    public Fatura(@NotNull @Positive Integer mes, @NotNull @Positive Integer ano, @NotNull Cartao cartao) {
        this.mes = mes;
        this.ano = ano;
        this.transacoes = new HashSet<>();
        this.cartao = cartao;
    }

    public Fatura(@NotNull @Positive Integer mes, @NotNull @Positive Integer ano, Set<Transacao> transacoes) {
        this.mes = mes;
        this.ano = ano;
        this.transacoes = transacoes;
    }

    public void adicionarTransacaoNaFatura(Transacao transacao){
        Assert.notNull(transacao, "A transação para ser adicionada na fatura não pode ser nula ");
        this.transacoes.add(transacao);
    }

    public BigDecimal calcularTotalDaFatura(){
        Assert.notNull(transacoes, "As lista de transações não pode ser nula para calcular o total da fatura");
        return transacoes.stream()
                .map(transacao -> transacao.retornarValorDaTransacao())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public FaturaResponse toResponse(){
        return new FaturaResponse(this.mes, this.ano, Transacao.toResponseSet(transacoes), calcularTotalDaFatura());
    }

    public BigDecimal calcularSaldoDoCartao(BigDecimal limite){
        return limite.subtract(calcularTotalDaFatura()).setScale(2, RoundingMode.CEILING);
    }

    public Set<Transacao> retornarAsUltimas10Transacoes() {
        return transacoes.stream().limit(10).collect(Collectors.toSet());
    }

    public boolean verificarSeCartaoPertenceAFatura(UUID numeroDoCartao){
        return this.cartao.getNumeroDoCartao().equals(numeroDoCartao);
    }

    public boolean verificarSeFaturaEDoMesCorrente(){
        return this.mes.equals(LocalDate.now().getMonthValue());
    }

    public UUID getId() {
        return id;
    }
}
