package com.marcoscoutozup.fatura.renegociarfatura;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RenegociarFaturaTests {

    private RenegociacaoDeFatura renegociacaoDeFatura;

    @BeforeEach
    public void setup(){
        renegociacaoDeFatura = new RenegociacaoDeFatura();
    }

    @Test
    @DisplayName("Deve lançar exceção ao associar fatura à renegociação se fatura for nula")
    public void deveLancarExcecaoAoAssociarFaturaARenegociacaoSeFaturaForNula(){
        assertThrows(IllegalArgumentException.class,
                () -> renegociacaoDeFatura.associarFaturaComRenegociacao(null));
    }

    @Test
    @DisplayName("Deve lançar exceção ao mudar o status da negociação se o mesmo for nulo")
    public void deveLancarExcecaoAoMudarStatusDaNegociacaoSeOMesmoForNulo(){
        assertThrows(IllegalArgumentException.class,
                () -> renegociacaoDeFatura.mudarStatusDaRenegociacao(null));
    }

}
