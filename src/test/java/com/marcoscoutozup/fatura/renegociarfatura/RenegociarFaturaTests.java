package com.marcoscoutozup.fatura.renegociarfatura;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RenegociarFaturaTests {

    private RenegociacaoDeFatura renegociacaoDeFatura;

    @BeforeEach
    public void setup(){
        renegociacaoDeFatura = new RenegociacaoDeFatura();
    }

    @Test
    @DisplayName("Deve lançar exceção ao associar fatura à renegociação se fatura for nula")
    public void deveLancarExcecaoAoAssociarFaturaARenegociacaoSeFaturaForNula(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> renegociacaoDeFatura.associarFaturaComRenegociacao(null));
    }

}
