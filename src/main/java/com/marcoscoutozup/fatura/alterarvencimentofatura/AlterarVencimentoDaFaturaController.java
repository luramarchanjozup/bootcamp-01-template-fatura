package com.marcoscoutozup.fatura.alterarvencimentofatura;

import com.marcoscoutozup.fatura.cartao.Cartao;
import com.marcoscoutozup.fatura.exceptions.StandardException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/cartoes")
public class AlterarVencimentoDaFaturaController {

    private final EntityManager entityManager;

    public AlterarVencimentoDaFaturaController(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostMapping("/{numeroDoCartao}/vencimentodafatura")
    @Transactional
    public ResponseEntity alterarDataDeVencimentoDaFatura(@PathVariable UUID numeroDoCartao,
                                                                                //1
                                                          @RequestBody @Valid VencimentoDaFaturaRequest vencimentoDaFaturaRequest){

                    //2
        final List<Cartao> cartaoProcurado = entityManager.createNamedQuery("findCartaoByNumero", Cartao.class)
                .setParameter("numeroDoCartao", numeroDoCartao)
                .getResultList();

        //3
        if(cartaoProcurado.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                //4
                    .body(new StandardException(HttpStatus.NOT_FOUND.value(), Arrays.asList("O cartão não foi encontrado")));
        }

        final Cartao cartao = cartaoProcurado.get(0);

        cartao.alterarDiaDeVencimentoDaFatura(vencimentoDaFaturaRequest.getDiaDeVencimento());
        entityManager.merge(cartao);

        return ResponseEntity.ok().build();
    }

}
