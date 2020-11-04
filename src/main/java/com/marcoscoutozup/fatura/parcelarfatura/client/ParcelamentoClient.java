package com.marcoscoutozup.fatura.parcelarfatura.client;

import com.marcoscoutozup.fatura.parcelarfatura.enums.StatusParcelamento;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(value = "Parcelamento", url = "${host.cartoes.url}", fallbackFactory = ParcelamentoClient.ParcelamentoClientFallbackFactory.class)
public interface ParcelamentoClient {

    Logger log = LoggerFactory.getLogger(ParcelamentoClient.class);

    @PostMapping("/api/cartoes/{numeroDoCartao}/parcelas")
    ParcelamentoDaFaturaResponseClient comunicarParcelamentoDeFatura(@PathVariable UUID numeroDoCartao, @RequestBody ParcelamentoDaFaturaRequestClient request);

    @Component
    class ParcelamentoClientFallbackFactory implements FallbackFactory<ParcelamentoClient> {
        @Override
        public ParcelamentoClient create(Throwable throwable) {
            log.error("[PARCELAMENTO DE FATURA] Erro ao comunicar com o sistema de cartões: {}", throwable.getMessage());
            return (numeroDoCartao, request) -> {
                ParcelamentoDaFaturaResponseClient response = new ParcelamentoDaFaturaResponseClient();
                response.setResultado(StatusParcelamento.NEGADO);
                return response;
            };
        }
    }
}
