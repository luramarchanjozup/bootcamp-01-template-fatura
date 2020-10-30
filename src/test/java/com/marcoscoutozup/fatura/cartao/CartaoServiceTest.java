package com.marcoscoutozup.fatura.cartao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;

public class CartaoServiceTest {

    private CartaoService cartaoService;

    @Mock
    private EntityManager entityManager;

    @BeforeEach
    public void setup(){
        cartaoService = new CartaoService(entityManager);
    }

    @Test
    @DisplayName("Deve lançar exceção se dados do cartão forem nulos")
    public void deveLancarExcecaoSeDadosDoCartaoForemNulos(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> cartaoService.verificarExistenciaDeCartao(null));
    }

}
