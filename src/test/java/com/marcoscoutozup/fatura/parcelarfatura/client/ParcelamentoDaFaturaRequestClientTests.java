package com.marcoscoutozup.fatura.parcelarfatura.client;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ParcelamentoDaFaturaRequestClientTests {

    @Test
    @DisplayName("Não deve construir a request para o client de parcelamento de fatura se o parcelamento for nulo")
    public void naoDeveContruirARequestParaOClientDeParcelamentoDeFaturaSeOParcelamentoForNulo(){
        assertThrows(IllegalArgumentException.class, () -> new ParcelamentoDaFaturaRequestClient(null));
    }
}
