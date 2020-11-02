package com.marcoscoutozup.fatura.fatura.parcelarfatura;

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
@RequestMapping("/cartoes/faturas/parcelar")
public class ParcelarFaturaController {

    private final EntityManager entityManager;
    private final Logger log = LoggerFactory.getLogger(ParcelarFaturaController.class);

    public ParcelarFaturaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/{numeroDoCartao}/{idFatura}")
    @Transactional
    public ResponseEntity parcelarFatura(@PathVariable UUID numeroDoCartao,
                                         @PathVariable UUID idFatura,
                                         @RequestBody @Valid ParcelamentoDeFaturaRequest parcelamento, //1
                                         UriComponentsBuilder uri){

                        //2
        final Optional<Fatura> faturaProcurada = Optional.ofNullable(entityManager.find(Fatura.class, idFatura));

        //3
        if(faturaProcurada.isEmpty()){
            log.warn("Fatura não encontrada, Id: {}", idFatura);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                //4
                    .body(new StandardException(HttpStatus.NOT_FOUND.value(), Arrays.asList("Fatura não encontrada")));
        }

        //5
        if(!faturaProcurada.get().verificarSeCartaoPertenceAFatura(numeroDoCartao)){
            log.warn("Fatura não pertence ao cartão, Número Do Cartão: {}", idFatura);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    //4
                    .body(new StandardException(HttpStatus.UNPROCESSABLE_ENTITY.value(), Arrays.asList("Fatura não pertence ao cartão")));
        }

        //6
        ParcelamentoDeFatura parcelamentoDeFatura = parcelamento.toParcelamentoDeFatura();
        entityManager.persist(parcelamentoDeFatura);

        return ResponseEntity.created(uri.path("/cartoes/parcelamentos/{id}")
                .buildAndExpand(parcelamentoDeFatura.getId())
                .toUri())
                .build();
    }
}
