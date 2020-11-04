package com.marcoscoutozup.fatura.alterarvencimentofatura.client;

import com.marcoscoutozup.fatura.alterarvencimentofatura.VencimentoDaFaturaRequest;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "AlteracaoDeVencimento", url = "${host.cartoes.url}",
        fallbackFactory = AlteracaoDeDataDeVencimentoClient.AlteracaoDeDataDeVencimentoClientFallbackFactory.class)
public interface AlteracaoDeDataDeVencimentoClient {

    Logger log = LoggerFactory.getLogger(AlteracaoDeDataDeVencimentoClient.class);

    @PostMapping("/api/cartoes/{numeroDoCartao}/vencimentos")
    ResponseEntity comunicarAlteracaoDeVencimentoDeFaturas(@PathVariable UUID numeroDoCartao,
                                                           @RequestBody VencimentoDaFaturaRequest request);

    @Component
    class AlteracaoDeDataDeVencimentoClientFallbackFactory implements FallbackFactory<AlteracaoDeDataDeVencimentoClient>{
        @Override
        public AlteracaoDeDataDeVencimentoClient create(Throwable throwable) {
            log.error("[ALTERAÇÃO DE DATA DE VENCIMENTO DA FATURA] Erro na comunicação com o sistema de cartões: {}", throwable.getMessage());
            return ((numeroDoCartao, request) -> ResponseEntity.badRequest().build());
        }
    }
}
