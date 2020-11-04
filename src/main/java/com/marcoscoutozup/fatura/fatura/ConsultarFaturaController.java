package com.marcoscoutozup.fatura.fatura;

import com.marcoscoutozup.fatura.cartao.Cartao;
import com.marcoscoutozup.fatura.exceptions.StandardException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ConsultarFaturaController {

    private final EntityManager entityManager;
    private final Logger log = LoggerFactory.getLogger(ConsultarFaturaCorrenteController.class);

    public ConsultarFaturaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GetMapping("/{numeroDoCartao}/faturas")
    public ResponseEntity buscarDetalhesDaFatura(@PathVariable UUID numeroDoCartao){

        log.info("[BUSCA DE DETALHES DA FATURA] Solicitação de detalhes da fatura do cartão: {}", numeroDoCartao);

        //1
        final List<Cartao> cartao = entityManager.createNamedQuery("findCartaoByNumero", Cartao.class)
                .setParameter("numeroDoCartao", numeroDoCartao)
                .getResultList();

        //2
        if(cartao.isEmpty()){
            log.warn("[BUSCA DE DETALHES DA FATURA] Cartão não foi encontrado. Número do cartão: {}", numeroDoCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    //3
                    .body(new StandardException(HttpStatus.NOT_FOUND.value(), Arrays.asList("O cartão não foi encontrado")));
        }

        //4
        final List<Fatura> fatura = entityManager.createNamedQuery("findFaturaByCartaoAndData", Fatura.class)
                .setParameter("numeroDoCartao", numeroDoCartao)
                .setParameter("mes", LocalDate.now().getMonthValue())
                .setParameter("ano", LocalDate.now().getYear())
                .getResultList();

        //5
        if(fatura.isEmpty()) {
            log.warn("[BUSCA DE DETALHES DA FATURA] Não foram encontradas transacões para a fatura do mês corrente. Número do cartão: {}", numeroDoCartao);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new StandardException(HttpStatus.NOT_FOUND.value(), Arrays.asList("Não existem transações para a fatura do mês corrente")));
        }

        //6
        final FaturaResponse response = fatura.get(0).toResponse();
        return ResponseEntity.ok(response);
    }
}
