package com.marcoscoutozup.fatura.alterarvencimentofatura;

import com.marcoscoutozup.fatura.alterarvencimentofatura.client.AlteracaoDeDataDeVencimentoClient;
import com.marcoscoutozup.fatura.cartao.Cartao;
import com.marcoscoutozup.fatura.exceptions.StandardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class AlterarVencimentoDaFaturaControllerTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery query;

    @Mock
    private AlteracaoDeDataDeVencimentoClient client;

    private AlterarVencimentoDaFaturaController controller;

    @BeforeEach
    public void setup(){
        initMocks(this);
        controller =  new AlterarVencimentoDaFaturaController(entityManager, client);
    }

    @Test
    @DisplayName("Deve retornar NotFound se cartão não for encontrado")
    public void deveRetornarNotFoundSeCartaoNaoForEncontrado(){
        when(entityManager.createNamedQuery(anyString(), any())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList());
        ResponseEntity responseEntity = controller.alterarDataDeVencimentoDaFatura(UUID.randomUUID(), null);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardException);
    }

    @Test
    @DisplayName("Não deve alterar data de vencimento de faturas do cartão se sistema de cartões retornar erro")
    public void naoDeveAlterarDataDeVencimentoDeFaturasDoCartaoSeSistemaDeCartoesRetornarErro(){
        when(entityManager.createNamedQuery(anyString(), any())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(new Cartao()));
        when(client.comunicarAlteracaoDeVencimentoDeFaturas(any(), any())).thenReturn(ResponseEntity.badRequest().build());
        ResponseEntity responseEntity = controller.alterarDataDeVencimentoDaFatura(UUID.randomUUID(), retornaVencimentoDaFaturaRequest());
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardException);
    }

    @Test
    @DisplayName("Deve modificar data de vencimento de faturas do cartão")
    public void deveModificarDataDeVencimentoDasFaturasDoCartao(){
        when(entityManager.createNamedQuery(anyString(), any())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(Arrays.asList(new Cartao()));
        when(client.comunicarAlteracaoDeVencimentoDeFaturas(any(), any())).thenReturn(ResponseEntity.ok().build());
        ResponseEntity responseEntity = controller.alterarDataDeVencimentoDaFatura(UUID.randomUUID(), retornaVencimentoDaFaturaRequest());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    private VencimentoDaFaturaRequest retornaVencimentoDaFaturaRequest(){
        VencimentoDaFaturaRequest vencimentoDaFaturaRequest = new VencimentoDaFaturaRequest();
        vencimentoDaFaturaRequest.setDia(new Random().nextInt(30)+1);
        return vencimentoDaFaturaRequest;
    }
}
