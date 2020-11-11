package com.marcoscoutozup.fatura.cartao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.MockitoAnnotations.initMocks;

public class CartaoTests {

    private Cartao cartao;

    @BeforeEach
    public void setup(){
        initMocks(this);
        cartao = new Cartao();
    }

    @Test
    @DisplayName("Não deve alterar data de vencimento nula")
    public void naoDeveAlterarDataDeVencimentoNula(){
        assertThrows(IllegalArgumentException.class,
                () -> cartao.alterarDiaDeVencimentoDaFatura(null));
    }
}
