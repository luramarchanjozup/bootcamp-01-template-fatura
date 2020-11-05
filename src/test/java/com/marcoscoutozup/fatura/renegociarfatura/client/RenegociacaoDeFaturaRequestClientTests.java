package com.marcoscoutozup.fatura.renegociarfatura.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class RenegociacaoDeFaturaRequestClientTests {

    @Test
    @DisplayName("Não deve construir a request para o client de renegociacao de fatura se a renegociação for nula")
    public void naoDeveContruirARequestParaOClientDeRenegociacaoDeFaturaSeARenegociacaoForNula(){
        assertThrows(IllegalArgumentException.class, () -> new RenegociacaoDeFaturaRequestClient(null));
    }
}
