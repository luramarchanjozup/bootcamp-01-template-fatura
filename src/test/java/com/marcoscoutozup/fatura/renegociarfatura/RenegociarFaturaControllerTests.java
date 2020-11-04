package com.marcoscoutozup.fatura.renegociarfatura;

import com.marcoscoutozup.fatura.exceptions.StandardException;
import com.marcoscoutozup.fatura.fatura.Fatura;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RenegociarFaturaControllerTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private Fatura fatura;

    @Mock
    private RenegociacaoDeFaturaRequest request;

    @Mock
    private UriComponentsBuilder uri;

    @Mock
    private UriComponents components;

    @Mock
    private ComunicarSistemaExternoDaRenegociacao comunicarSistemaExternoDaRenegociacao;

    private RenegociarFaturaController controller;

    @BeforeEach
    public void setup(){
        initMocks(this);
        controller = new RenegociarFaturaController(entityManager, comunicarSistemaExternoDaRenegociacao);
    }

    @Test
    @DisplayName("Deve retornar NotFound se não for encontrada a fatura")
    public void deveRetornarNotFoundSeNaoForEncontradaAFatura(){
        when(entityManager.find(any(), any())).thenReturn(null);
        ResponseEntity responseEntity = controller.renegociarFatura(UUID.randomUUID(), null, null, null);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getBody() instanceof StandardException);
    }

    @Test
    @DisplayName("Deve retornar Unprocessable Entity se a fatura não estiver relacionada com cartão")
    public void deveRetornarUnprocessableEntitySeAFaturaNaoEstiverRelacionadaComCartão(){
        when(entityManager.find(any(), any())).thenReturn(fatura);
        when(Optional.of(fatura).get().verificarSeCartaoPertenceAFatura(any())).thenReturn(false);
        ResponseEntity responseEntity = controller.renegociarFatura(UUID.randomUUID(), UUID.randomUUID(), null, null);
        Assertions.assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getBody() instanceof StandardException);
    }

    @Test
    @DisplayName("Deve cadastrar renegociação")
    public void deveCadastrarRenegociacaoDeFatura(){
        when(entityManager.find(any(), any())).thenReturn(fatura);
        when(Optional.of(fatura).get().verificarSeCartaoPertenceAFatura(any())).thenReturn(true);
        when(request.toRenegociacaoDeFatura()).thenReturn(new RenegociacaoDeFatura());
        when(uri.path(anyString())).thenReturn(uri);
        when(uri.buildAndExpand((Object) any())).thenReturn(components);
        when(components.toUri()).thenReturn(URI.create(""));
        ResponseEntity responseEntity = controller.renegociarFatura(UUID.randomUUID(), UUID.randomUUID(), request, uri);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertTrue(responseEntity.getHeaders().containsKey("Location"));
    }


}
