package com.marcoscoutozup.fatura.cartaovirtual;

import com.marcoscoutozup.fatura.cartao.Cartao;
import com.marcoscoutozup.fatura.exceptions.StandardException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
public class SolicitarCartaoVirtualController {

    private final EntityManager entityManager;
    private final VerificadorDeLimiteDoCartao verificarLimite; //1
    private final Logger log = LoggerFactory.getLogger(SolicitarCartaoVirtualController.class);

    public SolicitarCartaoVirtualController(EntityManager entityManager, VerificadorDeLimiteDoCartao verificarLimite) {
        this.entityManager = entityManager;
        this.verificarLimite = verificarLimite;
    }

    @PostMapping("/{idCartao}/cartaovirtual")
    @Transactional
    public ResponseEntity solicitarCartaoVirtual(@PathVariable UUID idCartao,
                                                 @RequestBody @Valid CartaoVirtualRequest cartaoVirtualRequest, //2
                                                 UriComponentsBuilder uri){

                        //3
        final Optional<Cartao> cartaoProcurado = Optional.ofNullable(entityManager.find(Cartao.class, idCartao));

        //4
        if(cartaoProcurado.isEmpty()){
            log.warn("[CARTÃO VIRTUAL] Cartão não encontrado: {}", idCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                //5
                    .body(new StandardException(HttpStatus.NOT_FOUND.value(), Arrays.asList("O cartão não foi encontrado")));
        }

        Cartao cartao = cartaoProcurado.get();

        //6
        if(!verificarLimite.limiteSolicitadoDoCartaoVirtualECompativel(cartao.getNumeroDoCartao(), cartaoVirtualRequest.getLimite())){
            log.warn("[CARTÃO VIRTUAL] Limite do cartão incompatível com a solicitação: {}", idCartao);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new StandardException(HttpStatus.UNPROCESSABLE_ENTITY.value(), Arrays.asList("Limite do cartão incompatível")));
        }

        CartaoVirtual cartaoVirtual = cartaoVirtualRequest.toCartaoVirtual();
        cartaoVirtual.associarCartaoComCartaoVirtual(cartao);
        entityManager.persist(cartaoVirtual);

        return ResponseEntity
                .created(uri.path("/cartoes/{idCartao}/cartoesvirtuais/{idCartaoVirtual}")
                .buildAndExpand(cartao.getId(), cartaoVirtual.getId())
                .toUri())
                .build();
    }

}
