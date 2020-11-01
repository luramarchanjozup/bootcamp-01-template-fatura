package com.marcoscoutozup.fatura.transacao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TransacaoTests {

    @Test
    @DisplayName("Deve lançar exceção se lista de transações para response for nula")
    public void deveLancarExcecaoSeListaDeTransacoesParaResponseForNula(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> Transacao.toResponseSet(null));
    }
}
