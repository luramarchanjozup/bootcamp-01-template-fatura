package com.marcoscoutozup.fatura.cartao;

import com.marcoscoutozup.fatura.cartao.client.CartaoClient;
import com.marcoscoutozup.fatura.saldocartao.ConsultarSaldoDoCartaoController;
import com.marcoscoutozup.fatura.saldocartao.LimiteResponse;
import com.marcoscoutozup.fatura.saldocartao.SaldoResponse;
import com.marcoscoutozup.fatura.saldocartao.VerificarSaldo;
import com.marcoscoutozup.fatura.exceptions.StandardException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class ConsultarSaldoDoCartaoControllerTests {

    @Mock
    private CartaoClient cartaoClient;

    @Mock
    private VerificarSaldo verificarSaldo;

    @Mock
    private ResponseEntity responseEntity;

    private ConsultarSaldoDoCartaoController controller;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        controller = new ConsultarSaldoDoCartaoController(cartaoClient, verificarSaldo);
    }

    @Test
    @DisplayName("Deve retornar Not Found quando cartão não for encontrado")
    public void deveRetornarNotFoundQuandoCartaoNaoForEncontrado(){
        when(cartaoClient.consultarlimiteDoCartao(any())).thenReturn(ResponseEntity.notFound().build());
        ResponseEntity responseEntity = controller.consultarSaldoDoCartao(UUID.randomUUID());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getBody() instanceof StandardException);
    }

    @Test
    @DisplayName("Deve retornar saldo calculado")
    public void deveRetornarSaldoCalculado(){
        when(cartaoClient.consultarlimiteDoCartao(any())).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(new LimiteResponse());
        when(verificarSaldo.calcularSaldoDisponivel(any(), any())).thenReturn(new SaldoResponse(new BigDecimal(0), new HashSet<>()));
        ResponseEntity responseEntity = controller.consultarSaldoDoCartao(UUID.randomUUID());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getBody() instanceof SaldoResponse);
    }

}
