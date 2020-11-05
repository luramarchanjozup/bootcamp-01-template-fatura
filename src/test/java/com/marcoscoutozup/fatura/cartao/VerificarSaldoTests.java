package com.marcoscoutozup.fatura.cartao;

import com.marcoscoutozup.fatura.saldocartao.VerificarSaldo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;

public class VerificarSaldoTests {

    @Mock
    private EntityManager entityManager;

    private VerificarSaldo verificarSaldo;

    @BeforeEach
    public void setup(){
        initMocks(this);
        verificarSaldo = new VerificarSaldo(entityManager);
    }

    @Test
    @DisplayName("Deve lançar exceção se número do cartão for nulo")
    public void deveLancarExcecaoSeNumeroDoCartaoForNulo(){
        assertThrows(IllegalArgumentException.class, () -> verificarSaldo.calcularSaldoDisponivel(null, new BigDecimal(0)));
    }

    @Test
    @DisplayName("Deve lançar exceção se limite for nulo")
    public void deveLancarExcecaoSeLimiteForNulo(){
        assertThrows(IllegalArgumentException.class, () -> verificarSaldo.calcularSaldoDisponivel(UUID.randomUUID(), null));
    }

}
