package com.marcoscoutozup.fatura.renegociarfatura;

import com.marcoscoutozup.fatura.exceptions.StandardException;
import com.marcoscoutozup.fatura.fatura.Fatura;
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
public class RenegociarFaturaController {

    private final EntityManager entityManager;
    private final ComunicarSistemaExternoDaRenegociacao comunicarSistemaExternoDaRenegociacao; //1
    private final Logger log = LoggerFactory.getLogger(RenegociarFaturaController.class);

    public RenegociarFaturaController(EntityManager entityManager, ComunicarSistemaExternoDaRenegociacao comunicarSistemaExternoDaRenegociacao) {
        this.entityManager = entityManager;
        this.comunicarSistemaExternoDaRenegociacao = comunicarSistemaExternoDaRenegociacao;
    }

    @PostMapping("/{numeroDoCartao}/faturas/{idFatura}/renegociar")
    @Transactional
    public ResponseEntity renegociarFatura(@PathVariable UUID numeroDoCartao,
                                           @PathVariable UUID idFatura,
                                                                //2
                                           @RequestBody @Valid RenegociacaoDeFaturaRequest renegociacao,
                                           UriComponentsBuilder uri){

                        //3
        final Optional<Fatura> faturaProcurada = Optional.ofNullable(entityManager.find(Fatura.class, idFatura));

        //4
        if(faturaProcurada.isEmpty()){
            log.warn("Fatura não encontrada, Id: {}", idFatura);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                //5
                    .body(new StandardException(HttpStatus.NOT_FOUND.value(), Arrays.asList("Fatura não encontrada")));
        }

        Fatura fatura = faturaProcurada.get();

        //6
        if(!fatura.verificarSeCartaoPertenceAFatura(numeroDoCartao)){
            log.warn("Fatura não pertence ao cartão, Número Do Cartão: {}", idFatura);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new StandardException(HttpStatus.UNPROCESSABLE_ENTITY.value(), Arrays.asList("Fatura não pertence ao cartão")));
        }

        //7
        RenegociacaoDeFatura renegociacaoDeFatura = renegociacao.toRenegociacaoDeFatura();
        renegociacaoDeFatura.associarFaturaComRenegociacao(fatura);
        entityManager.persist(renegociacaoDeFatura);

        comunicarSistemaExternoDaRenegociacao.comunicarSistemaExternoSobreParcelamentoDeFatura(numeroDoCartao, renegociacaoDeFatura);

        return ResponseEntity.created(uri.path("/cartoes/{id}/renegociacoes")
                .buildAndExpand(renegociacaoDeFatura.getId())
                .toUri())
                .build();
    }
}
