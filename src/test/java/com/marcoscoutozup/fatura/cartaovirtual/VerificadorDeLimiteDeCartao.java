package com.marcoscoutozup.fatura.cartaovirtual;

import com.marcoscoutozup.fatura.cartao.client.CartaoClient;
import com.marcoscoutozup.fatura.saldocartao.LimiteResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class VerificadorDeLimiteDeCartao {

    @Mock
    private CartaoClient cartaoClient;

    private VerificadorDeLimiteDoCartao verificadorDeLimiteDoCartao;

    @BeforeEach
    public void setup(){
        initMocks(this);
        verificadorDeLimiteDoCartao = new VerificadorDeLimiteDoCartao(cartaoClient);
    }

    @Test
    @DisplayName("Deve retornar false se status code da resposta não for 2xx")
    public void deveRetornarFalseSeStatusCodeDaRespostaNaoFor2xx(){
        when(cartaoClient.consultarlimiteDoCartao(any())).thenReturn(ResponseEntity.badRequest().build());
        boolean resultado = verificadorDeLimiteDoCartao.limiteSolicitadoDoCartaoVirtualECompativel(UUID.randomUUID(), new BigDecimal(100));
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Deve retornar false se o limite do cartão virtual for incompatível com cartão")
    public void deveRetornarFalseSeOLimiteDoCartaoVirtualForIncompativelComCartao(){
        when(cartaoClient.consultarlimiteDoCartao(any())).thenReturn(ResponseEntity.ok(retornarLimiteResponse(500)));
        boolean resultado = verificadorDeLimiteDoCartao.limiteSolicitadoDoCartaoVirtualECompativel(UUID.randomUUID(), new BigDecimal(1000));
        assertFalse(resultado);
    }

    @Test
    @DisplayName("Deve retornar true se o limite do cartão virtual for compatível com cartão")
    public void deveRetornarTrueSeOLimiteDoCartaoVirtualForCompativelComCartao(){
        when(cartaoClient.consultarlimiteDoCartao(any())).thenReturn(ResponseEntity.ok(retornarLimiteResponse(500)));
        boolean resultado = verificadorDeLimiteDoCartao.limiteSolicitadoDoCartaoVirtualECompativel(UUID.randomUUID(), new BigDecimal(100));
        assertTrue(resultado);
    }

    public LimiteResponse retornarLimiteResponse(Integer limite){
        LimiteResponse response = new LimiteResponse();
        response.setLimite(new BigDecimal(limite));
        return response;
    }
}
