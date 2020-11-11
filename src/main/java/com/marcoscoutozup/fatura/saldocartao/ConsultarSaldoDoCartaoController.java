package com.marcoscoutozup.fatura.saldocartao;


import com.marcoscoutozup.fatura.cartao.Cartao;
import com.marcoscoutozup.fatura.cartao.client.CartaoClient;
import com.marcoscoutozup.fatura.exceptions.StandardException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
public class ConsultarSaldoDoCartaoController {

    private final EntityManager entityManager;
                    //1
    private final CartaoClient cartaoClient;
                    //2
    private final VerificarSaldo verificarSaldo;


    private final Logger log = LoggerFactory.getLogger(ConsultarSaldoDoCartaoController.class);

    public ConsultarSaldoDoCartaoController(CartaoClient cartaoClient, VerificarSaldo verificarSaldo, EntityManager entityManager) {
        this.cartaoClient = cartaoClient;
        this.verificarSaldo = verificarSaldo;
        this.entityManager = entityManager;
    }

    @GetMapping("/{idCartao}/saldo")
    public ResponseEntity consultarSaldoDoCartao(@PathVariable UUID idCartao){

        log.info("[CONSULTA DE SALDO DO CARTÃO] Processando pedido de consulta de saldo do cartão");

        final Optional<Cartao> cartaoProcurado = Optional.ofNullable(entityManager.find(Cartao.class, idCartao));

        //4
        if(cartaoProcurado.isEmpty()){
            log.warn("[CONSULTA DE SALDO DO CARTÃO] O cartão não foi encontrado no sistema externo, cartão: {}", idCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                //5
                    .body(new StandardException(HttpStatus.NOT_FOUND.value(), Arrays.asList("O cartão não foi encontrado")));
        }

        Cartao cartao = cartaoProcurado.get();

        //3
        final ResponseEntity<LimiteResponse> response =
                cartaoClient.consultarlimiteDoCartao(cartao.getNumeroDoCartao());

        //6
        final SaldoResponse saldo =
                verificarSaldo.calcularSaldoDisponivel(cartao.getId(), response.getBody().getLimite());

        return ResponseEntity.ok(saldo);
    }
}
