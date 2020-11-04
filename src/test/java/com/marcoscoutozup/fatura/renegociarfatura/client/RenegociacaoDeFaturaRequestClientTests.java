package com.marcoscoutozup.fatura.renegociarfatura.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class RenegociacaoDeFaturaRequestClientTests {

    @Test
    @DisplayName("Não deve construir a request para o client de renegociacao de fatura se a renegociação for nula")
    public void naoDeveContruirARequestParaOClientDeRenegociacaoDeFaturaSeARenegociacaoForNula(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> new RenegociacaoDeFaturaRequestClient(null));
    }
}
