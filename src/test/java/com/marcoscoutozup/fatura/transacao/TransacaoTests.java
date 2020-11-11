package com.marcoscoutozup.fatura.transacao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class TransacaoTests {

    @Test
    @DisplayName("Deve lançar exceção se lista de transações para response for nula")
    public void deveLancarExcecaoSeListaDeTransacoesParaResponseForNula(){
        assertThrows(IllegalArgumentException.class,
                () -> Transacao.toResponseSet(null));
    }
}
