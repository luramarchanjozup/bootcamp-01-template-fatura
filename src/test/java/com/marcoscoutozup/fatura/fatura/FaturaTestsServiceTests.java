package com.marcoscoutozup.fatura.fatura;

import com.marcoscoutozup.fatura.cartao.Cartao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import javax.persistence.EntityManager;

public class FaturaTestsServiceTests {

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
        Assertions.assertThrows(IllegalArgumentException.class, () -> faturaService.verificarExistenciaDeFatura(null, 0));
    }

    @Test
    @DisplayName("Deve lançar exceção se mês da transação for nulo")
    public void deveLancarExcecaoSeMesDaTransacaoForNulo(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> faturaService.verificarExistenciaDeFatura(new Cartao(), null));
    }
}
