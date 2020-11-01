package com.marcoscoutozup.fatura.fatura;

import com.marcoscoutozup.fatura.cartao.Cartao;
import com.marcoscoutozup.fatura.exceptions.StandardException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

public class ConsultarFaturaTestsControllerTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private TypedQuery query;

    private ConsultarFaturaController controller;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        controller = new ConsultarFaturaController(entityManager);
    }

    @Test
    @DisplayName("Deve retornar NotFound quanto cartão não for encontrado")
    public void deveRetornarNotFoundQuandoCartaoNaoForEncontrado(){
        when(entityManager.createNamedQuery(any(), any(Class.class))).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(new ArrayList());
        ResponseEntity responseEntity = controller.buscarDetalhesDaFatura(UUID.randomUUID());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getBody() instanceof StandardException);
    }

    @Test
    @DisplayName("Deve retornar NotFound quanto não houver transações para o cartão")
    public void deveRetornarNotFoundQuandoNaoHouverTransacaoParaOCartao(){
        when(entityManager.createNamedQuery(anyString(), any())).thenReturn(query);
        when(query.setParameter(anyString(), any())).thenReturn(query);
        when(query.getResultList()).thenReturn(List.of(new Cartao()));
        when(query.getResultList()).thenReturn(new ArrayList());
        ResponseEntity responseEntity = controller.buscarDetalhesDaFatura(UUID.randomUUID());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getBody() instanceof StandardException);
    }

}
