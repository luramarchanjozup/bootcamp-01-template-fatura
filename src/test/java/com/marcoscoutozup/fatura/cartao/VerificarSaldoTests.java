package com.marcoscoutozup.fatura.cartao;

import com.marcoscoutozup.fatura.saldocartao.VerificarSaldo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.UUID;

public class VerificarSaldoTests {

    @Mock
    private EntityManager entityManager;

    private VerificarSaldo verificarSaldo;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        verificarSaldo = new VerificarSaldo(entityManager);
    }

    @Test
    @DisplayName("Deve lançar exceção se número do cartão for nulo")
    public void deveLancarExcecaoSeNumeroDoCartaoForNulo(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> verificarSaldo.verificarSaldoDisponivelNoCartao(null, new BigDecimal(0)));
    }

    @Test
    @DisplayName("Deve lançar exceção se limite for nulo")
    public void deveLancarExcecaoSeLimiteForNulo(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> verificarSaldo.verificarSaldoDisponivelNoCartao(UUID.randomUUID(), null));
    }

}
