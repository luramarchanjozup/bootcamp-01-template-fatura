package com.marcoscoutozup.fatura.saldocartao;


import com.marcoscoutozup.fatura.cartao.CartaoClient;
import com.marcoscoutozup.fatura.exceptions.StandardException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
public class ConsultarSaldoDoCartaoController {

                    //1
    private final CartaoClient cartaoClient;
                    //2
    private final VerificarSaldo verificarSaldo;

    private final Logger log = LoggerFactory.getLogger(ConsultarSaldoDoCartaoController.class);

    public ConsultarSaldoDoCartaoController(CartaoClient cartaoClient, VerificarSaldo verificarSaldo) {
        this.cartaoClient = cartaoClient;
        this.verificarSaldo = verificarSaldo;
    }

    @GetMapping("/{numeroDoCartao}/saldo")
    public ResponseEntity consultarSaldoDoCartao(@PathVariable UUID numeroDoCartao){

                        //3
        final ResponseEntity<LimiteResponse> response =
                cartaoClient.consultarlimiteDoCartao(numeroDoCartao);

        //4
        if(response.getStatusCode() == HttpStatus.NOT_FOUND){
            log.warn("O cartão não foi encontrado no sistema externo, Número do cartão: {}", numeroDoCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                //5
                    .body(new StandardException(HttpStatus.NOT_FOUND.value(), Arrays.asList("O cartão não foi encontrado")));
        }

        //6
        final SaldoResponse saldo =
                verificarSaldo.verificarSaldoDisponivelNoCartao(numeroDoCartao, response.getBody().getLimite());

        return ResponseEntity.ok(saldo);
    }
}
