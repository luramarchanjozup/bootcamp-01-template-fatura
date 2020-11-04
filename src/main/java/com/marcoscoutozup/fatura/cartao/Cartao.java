package com.marcoscoutozup.fatura.cartao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.UUID;

@Entity
@NamedQuery(name = "findCartaoByNumero", query = " select c from Cartao c where numeroDoCartao = :numeroDoCartao")
public class Cartao {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotNull
    private UUID numeroDoCartao;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Min(1)
    @Max(31)
    private Integer diaDeVencimentoDasFaturas;

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotNull UUID numeroDoCartao, @NotBlank @Email String email) {
        this.numeroDoCartao = numeroDoCartao;
        this.email = email;
        this.diaDeVencimentoDasFaturas = 10;
    }

    public void alterarDiaDeVencimentoDaFatura(Integer diaDeVencimentoDasFaturas) {
        Assert.notNull(diaDeVencimentoDasFaturas, "Dia de vencimento da fatura não pode ser nulo");
        this.diaDeVencimentoDasFaturas = diaDeVencimentoDasFaturas;
    }

    public UUID getNumeroDoCartao() {
        return numeroDoCartao;
    }

}
