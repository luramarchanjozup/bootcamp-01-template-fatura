package com.marcoscoutozup.fatura.parcelarfatura;

import com.marcoscoutozup.fatura.exceptions.StandardException;
import com.marcoscoutozup.fatura.fatura.Fatura;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.*;

public class ParcelarFaturaControllerTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Fatura fatura;

    @Mock
    private ParcelamentoDeFaturaRequest request;

    @Mock
    private UriComponentsBuilder uri;

    @Mock
    private UriComponents components;

    @Mock
    private ComunicarSistemaExternoDoParcelamento client;

    private ParcelarFaturaController controller;

    @BeforeEach
    public void setup(){
        initMocks(this);
        controller = new ParcelarFaturaController(entityManager, client);
    }

    @Test
    @DisplayName("Deve retornar NotFound se não for encontrada a fatura")
    public void deveRetornarNotFoundSeNaoForEncontradaAFatura(){
        when(entityManager.find(any(), any())).thenReturn(null);
        ResponseEntity responseEntity = controller.parcelarFatura(UUID.randomUUID(), null, null, null);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardException);
    }

    @Test
    @DisplayName("Deve retornar Unprocessable Entity se a fatura não estiver relacionada com cartão")
    public void deveRetornarUnprocessableEntitySeAFaturaNaoEstiverRelacionadaComCartão(){
        when(entityManager.find(any(), any())).thenReturn(fatura);
        when(Optional.of(fatura).get().verificarSeCartaoPertenceAFatura(any())).thenReturn(false);
        ResponseEntity responseEntity = controller.parcelarFatura(UUID.randomUUID(), UUID.randomUUID(), null, null);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardException);
    }

    @Test
    @DisplayName("Deve cadastrar fatura")
    public void deveCadastrarFatura(){
        when(entityManager.find(any(), any())).thenReturn(fatura);
        when(Optional.of(fatura).get().verificarSeCartaoPertenceAFatura(any())).thenReturn(true);
        when(Optional.of(fatura).get().verificarSeFaturaEDoMesCorrente()).thenReturn(true);
        when(request.toParcelamentoDeFatura()).thenReturn(new ParcelamentoDeFatura());
        when(uri.path(anyString())).thenReturn(uri);
        when(uri.buildAndExpand((Object) any())).thenReturn(components);
        when(components.toUri()).thenReturn(URI.create(""));
        ResponseEntity responseEntity = controller.parcelarFatura(UUID.randomUUID(), UUID.randomUUID(), request, uri);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(responseEntity.getHeaders().containsKey("Location"));
    }

}
