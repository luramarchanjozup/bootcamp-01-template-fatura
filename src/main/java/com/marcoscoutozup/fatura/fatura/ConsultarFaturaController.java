package com.marcoscoutozup.fatura.fatura;

import com.marcoscoutozup.fatura.cartao.Cartao;
import com.marcoscoutozup.fatura.exceptions.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes/faturas")
public class ConsultarFaturaController {

    private final EntityManager entityManager;

    public ConsultarFaturaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @GetMapping("/{id}")
    public ResponseEntity buscarDetalhesDaFatura(@PathVariable UUID id){
                    //1
        final List<Cartao> cartao = entityManager.createNamedQuery("findCartaoByNumero", Cartao.class)
                .setParameter("numeroDoCartao", id)
                .getResultList();

        //2
        if(cartao.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                //3
                    .body(new StandardException(HttpStatus.NOT_FOUND.value(), Arrays.asList("O cartão não foi encontrado")));
        }

                    //4
        final List<Fatura> fatura = entityManager.createNamedQuery("findFaturaByCartaoAndMesCorrente", Fatura.class)
                .setParameter("numeroDoCartao", id)
                .setParameter("mesCorrespondente", LocalDate.now().getMonth().getValue())
                .getResultList();

        //5
        if(fatura.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new StandardException(HttpStatus.NOT_FOUND.value(), Arrays.asList("Não existem transações para a fatura do mês corrente")));
        }

        //6
        FaturaResponse response = fatura.get(0).toResponse();
        return ResponseEntity.ok(response);
    }
}
