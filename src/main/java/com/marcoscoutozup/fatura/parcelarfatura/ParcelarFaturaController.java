package com.marcoscoutozup.fatura.parcelarfatura;

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
public class ParcelarFaturaController {

    private final EntityManager entityManager;
    private final ComunicarSistemaExternoDoParcelamento client; //1
    private final Logger log = LoggerFactory.getLogger(ParcelarFaturaController.class);

    public ParcelarFaturaController(EntityManager entityManager, ComunicarSistemaExternoDoParcelamento client) {
        this.entityManager = entityManager;
        this.client = client;
    }

    @PostMapping("/{numeroDoCartao}/faturas/{idFatura}/parcelar")
    @Transactional
    public ResponseEntity parcelarFatura(@PathVariable UUID numeroDoCartao,
                                         @PathVariable UUID idFatura,
                                                                //2
                                         @RequestBody @Valid ParcelamentoDeFaturaRequest parcelamento,
                                         UriComponentsBuilder uri){

        log.info("[PARCELAMENTO DE FATURA] Processando pedido de parcelamento de fatura");
                        //3
        final Optional<Fatura> faturaProcurada = Optional.ofNullable(entityManager.find(Fatura.class, idFatura));

        //4
        if(faturaProcurada.isEmpty()){
            log.warn("[PARCELAMENTO DE FATURA] Fatura não encontrada, Id: {}", idFatura);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                //5
                    .body(new StandardException(HttpStatus.NOT_FOUND.value(), Arrays.asList("Fatura não encontrada")));
        }

        Fatura fatura = faturaProcurada.get();

        //6
        if(!fatura.verificarSeCartaoPertenceAFatura(numeroDoCartao) || !fatura.verificarSeFaturaEDoMesCorrente()){
            log.warn("[PARCELAMENTO DE FATURA] Fatura não é válida para parcelamento, Fatura: {}", idFatura);
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .body(new StandardException(HttpStatus.UNPROCESSABLE_ENTITY.value(), Arrays.asList("Fatura não é válida para parcelamento")));
        }

        //7
        ParcelamentoDeFatura parcelamentoDeFatura = parcelamento.toParcelamentoDeFatura();
        parcelamentoDeFatura.relacionarFaturaAoParcelamento(fatura);
        entityManager.persist(parcelamentoDeFatura);

        client.comunicarSistemaExternoSobreParcelamentoDeFatura(numeroDoCartao, parcelamentoDeFatura);

        return ResponseEntity.created(uri.path("/cartoes/{id}/parcelamentos")
                .buildAndExpand(parcelamentoDeFatura.getId())
                .toUri())
                .build();
    }
}
