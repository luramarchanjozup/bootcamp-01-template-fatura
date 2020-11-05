package com.marcoscoutozup.fatura.fatura;

import com.marcoscoutozup.fatura.cartao.Cartao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class FaturaServiceTests {

    private FaturaService faturaService;

    @Mock
    private EntityManager entityManager;

    @BeforeEach
    public void setup(){
        faturaService = new FaturaService(entityManager);
    }

    @Test
    @DisplayName("Deve lançar exceção se cartão for nulo")
    public void deveLancarExcecaoSeCartaoForNulo(){
        assertThrows(IllegalArgumentException.class, () -> faturaService.verificarExistenciaDeFatura(null, 1, 1));
    }

    @Test
    @DisplayName("Deve lançar exceção se mês da transação for nulo")
    public void deveLancarExcecaoSeMesDaTransacaoForNulo(){
        assertThrows(IllegalArgumentException.class, () -> faturaService.verificarExistenciaDeFatura(new Cartao(), null, 1));
    }

    @Test
    @DisplayName("Deve lançar exceção se ano da transação for nulo")
    public void deveLancarExcecaoSeAnoDaTransacaoForNulo(){
        assertThrows(IllegalArgumentException.class, () -> faturaService.verificarExistenciaDeFatura(new Cartao(), 1, null));
    }
}
