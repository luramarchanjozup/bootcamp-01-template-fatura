package com.marcoscoutozup.fatura.cartao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProcessarCartaoTest {

    private ProcessarCartao processarCartao;

    @Mock
    private EntityManager entityManager;

    @BeforeEach
    public void setup(){
        processarCartao = new ProcessarCartao(entityManager);
    }

    @Test
    @DisplayName("Deve lançar exceção se dados do cartão forem nulos")
    public void deveLancarExcecaoSeDadosDoCartaoForemNulos(){
        assertThrows(IllegalArgumentException.class,
                () -> processarCartao.verificarExistenciaDeCartao(null));
    }

}
