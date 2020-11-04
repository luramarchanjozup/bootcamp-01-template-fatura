package com.marcoscoutozup.fatura.alterarvencimentofatura;

import com.marcoscoutozup.fatura.alterarvencimentofatura.client.AlteracaoDeDataDeVencimentoClient;
import com.marcoscoutozup.fatura.cartao.Cartao;
import com.marcoscoutozup.fatura.exceptions.StandardException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
public class AlterarVencimentoDaFaturaController {

    private final EntityManager entityManager;
    private final AlteracaoDeDataDeVencimentoClient client;
    private final Logger log = LoggerFactory.getLogger(AlterarVencimentoDaFaturaController.class);

    public AlterarVencimentoDaFaturaController(EntityManager entityManager, AlteracaoDeDataDeVencimentoClient client) {
        this.entityManager = entityManager;
        this.client = client;
    }

    @PostMapping("/{idCartao}/vencimentodafatura")
    @Transactional
    public ResponseEntity alterarDataDeVencimentoDaFatura(@PathVariable UUID idCartao,
                                                                                //1
                                                          @RequestBody @Valid VencimentoDaFaturaRequest vencimentoDaFaturaRequest){

        log.warn("[ALTERAÇÃO DE DATA DE VENCIMENTO DA FATURA] Processando alteração de data de vencimento da fatura");

                        //2
        final Optional<Cartao> cartaoProcurado = Optional.ofNullable(entityManager.find(Cartao.class, idCartao));

        //3
        if(cartaoProcurado.isEmpty()){
            log.warn("[ALTERAÇÃO DE DATA DE VENCIMENTO DA FATURA] Cartão não encontrado: {}", idCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                //4
                    .body(new StandardException(HttpStatus.NOT_FOUND.value(),
                            Arrays.asList("O cartão não foi encontrado")));
        }

        final Cartao cartao = cartaoProcurado.get();

        ResponseEntity responseEntity =
                client.comunicarAlteracaoDeVencimentoDeFaturas(cartao.getNumeroDoCartao(), vencimentoDaFaturaRequest);

        //5
        if(!responseEntity.getStatusCode().is2xxSuccessful()){
            log.warn("[ALTERAÇÃO DE DATA DE VENCIMENTO DA FATURA] Solicitação de mudança no vencimento do cartão negada, cartão: {}", idCartao);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new StandardException(HttpStatus.UNPROCESSABLE_ENTITY.value(),
                            Arrays.asList("Alteração na data de vencimento do cartão negada")));
        }

        cartao.alterarDiaDeVencimentoDaFatura(vencimentoDaFaturaRequest.getDia());
        entityManager.merge(cartao);

        return ResponseEntity.ok().build();
    }

}
