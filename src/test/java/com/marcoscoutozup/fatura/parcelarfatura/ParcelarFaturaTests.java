package com.marcoscoutozup.fatura.parcelarfatura;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

public class ParcelarFaturaTests {

    private ParcelamentoDeFatura parcelamentoDeFatura;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        parcelamentoDeFatura = new ParcelamentoDeFatura();
    }

    @Test
    @DisplayName("Deve lançar exceção ao associar fatura ao parcelamento se fatura for nula")
    public void deveLancarExcecaoAoAssociarFaturaAoParcelamentoSeFaturaForNula(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> parcelamentoDeFatura.relacionarFaturaAoParcelamento(null));
    }

    @Test
    @DisplayName("Deve lançar exceção ao mudar o status de pagamento se o status for nulo")
    public void deveLancarExcecaoAoMudarStatusDePagamentoSeOStatusForNulo(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> parcelamentoDeFatura.mudarStatusDoParcelamento(null));
    }

}
