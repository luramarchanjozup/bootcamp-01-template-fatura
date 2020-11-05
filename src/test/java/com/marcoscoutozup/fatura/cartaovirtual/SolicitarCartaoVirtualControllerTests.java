package com.marcoscoutozup.fatura.cartaovirtual;

import com.marcoscoutozup.fatura.cartao.Cartao;
import com.marcoscoutozup.fatura.exceptions.StandardException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class SolicitarCartaoVirtualControllerTests {

    @Mock
    private EntityManager entityManager;

    @Mock
    private VerificadorDeLimiteDoCartao verificarLimite;

    private SolicitarCartaoVirtualController controller;

    @BeforeEach
    public void setup(){
        initMocks(this);
        controller = new SolicitarCartaoVirtualController(entityManager, verificarLimite);
    }

    @Test
    @DisplayName("Deve retornar NotFound se cartão não for encontrado")
    public void deveRetornarNotFoundSeCartaoNaoForEncontrado(){
        when(entityManager.find(any(), any())).thenReturn(null);
        ResponseEntity responseEntity = controller.solicitarCartaoVirtual(UUID.randomUUID(), null, null);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardException);
    }

    @Test
    @DisplayName("Deve retornar UnprocessableEntity se limite de cartão for incompatível")
    public void deveRetornarUnprocessableEntitySeLimiteDeCartaoForIncompativel(){
        when(entityManager.find(any(), any())).thenReturn(new Cartao());
        when(verificarLimite.limiteSolicitadoDoCartaoVirtualECompativel(any(), any())).thenReturn(false);
        ResponseEntity responseEntity = controller.solicitarCartaoVirtual(UUID.randomUUID(), new CartaoVirtualRequest(), null);
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody() instanceof StandardException);
    }

    @Test
    @DisplayName("Deve cadastrar cartão virtual")
    public void deveCadastrarCartãoVirtual(){
        when(entityManager.find(any(), any())).thenReturn(new Cartao());
        when(verificarLimite.limiteSolicitadoDoCartaoVirtualECompativel(any(), any())).thenReturn(true);
        ResponseEntity responseEntity = controller.solicitarCartaoVirtual(UUID.randomUUID(), new CartaoVirtualRequest(), UriComponentsBuilder.newInstance());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertTrue(responseEntity.getHeaders().containsKey("Location"));
    }

}
