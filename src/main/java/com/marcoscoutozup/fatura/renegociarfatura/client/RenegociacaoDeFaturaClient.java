package com.marcoscoutozup.fatura.renegociarfatura.client;

import com.marcoscoutozup.fatura.renegociarfatura.enums.StatusDaRenegociacao;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(value = "Renegociacao", url = "${host.cartoes.url}", fallbackFactory = RenegociacaoDeFaturaClient.RenegociacaoDeFaturaClientFallbackFactory.class)
public interface RenegociacaoDeFaturaClient {

    Logger log = LoggerFactory.getLogger(RenegociacaoDeFaturaClient.class);

    @PostMapping("/api/cartoes/{numeroDoCartao}/renegociacoes")
    RenegociacaoDeFaturaResponseClient comunicarRenegociacaoDeFatura(@PathVariable UUID numeroDoCartao, @RequestBody RenegociacaoDeFaturaRequestClient request);

    @Component
    class RenegociacaoDeFaturaClientFallbackFactory implements FallbackFactory<RenegociacaoDeFaturaClient> {

        @Override
        public RenegociacaoDeFaturaClient create(Throwable throwable) {
            log.error("[RENEGOCIAÇÃO DE FATURA] Erro ao comunicar com o sistema de cartões: {}", throwable.getMessage());
            return ((numeroDoCartao, request) -> {
                RenegociacaoDeFaturaResponseClient response = new RenegociacaoDeFaturaResponseClient();
                response.setResultado(StatusDaRenegociacao.NEGADO);
                return response;
            });
        }
    }
}
