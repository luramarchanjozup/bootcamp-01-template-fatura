package com.marcoscoutozup.fatura.cartao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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

    @Deprecated
    public Cartao() {
    }

    public Cartao(@NotNull UUID numeroDoCartao, @NotBlank @Email String email) {
        this.numeroDoCartao = numeroDoCartao;
        this.email = email;
    }

    public UUID getNumeroDoCartao() {
        return numeroDoCartao;
    }

}
