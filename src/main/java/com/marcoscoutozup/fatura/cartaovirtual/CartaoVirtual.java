package com.marcoscoutozup.fatura.cartaovirtual;

import com.marcoscoutozup.fatura.cartao.Cartao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
public class CartaoVirtual {

    @Id
    @GeneratedValue(generator = "uuid4")
    private UUID id;

    @NotNull
    @Positive
    private BigDecimal limite;

    @NotNull
    @ManyToOne
    private Cartao cartao;

    @Deprecated
    public CartaoVirtual() {
    }

    public CartaoVirtual(@NotNull @Positive BigDecimal limite) {
        this.limite = limite;
    }

    public void associarCartaoComCartaoVirtual(Cartao cartao) {
        this.cartao = cartao;
    }

    public UUID getId() {
        return id;
    }
}
