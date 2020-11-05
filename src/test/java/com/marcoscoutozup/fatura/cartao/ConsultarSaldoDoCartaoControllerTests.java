package com.marcoscoutozup.fatura.cartao;

import com.marcoscoutozup.fatura.cartao.client.CartaoClient;
import com.marcoscoutozup.fatura.exceptions.StandardException;
import com.marcoscoutozup.fatura.saldocartao.ConsultarSaldoDoCartaoController;
import com.marcoscoutozup.fatura.saldocartao.LimiteResponse;
import com.marcoscoutozup.fatura.saldocartao.SaldoResponse;
import com.marcoscoutozup.fatura.saldocartao.VerificarSaldo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConsultarSaldoDoCartaoControllerTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private CartaoClient cartaoClient;

    @Mock
    private VerificarSaldo verificarSaldo;

    @Mock
    private ResponseEntity responseEntity;

    private ConsultarSaldoDoCartaoController controller;

    @BeforeEach
    public void setup(){
        initMocks(this);
        controller = new ConsultarSaldoDoCartaoController(cartaoClient, verificarSaldo, entityManager);
    }

    @Test
    @DisplayName("Deve retornar Not Found quando cartão não for encontrado")
    public void deveRetornarNotFoundQuandoCartaoNaoForEncontrado(){
        when(entityManager.find(any(), any())).thenReturn(null);
        ResponseEntity responseEntity = controller.consultarSaldoDoCartao(UUID.randomUUID());
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardException);
    }

    @Test
    @DisplayName("Deve retornar saldo calculado")
    public void deveRetornarSaldoCalculado(){
        when(entityManager.find(any(), any())).thenReturn(new Cartao());
        when(cartaoClient.consultarlimiteDoCartao(any())).thenReturn(responseEntity);
        when(responseEntity.getBody()).thenReturn(new LimiteResponse());
        when(verificarSaldo.calcularSaldoDisponivel(any(), any())).thenReturn(new SaldoResponse(new BigDecimal(0), new HashSet<>()));
        ResponseEntity responseEntity = controller.consultarSaldoDoCartao(UUID.randomUUID());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof SaldoResponse);
    }

}
