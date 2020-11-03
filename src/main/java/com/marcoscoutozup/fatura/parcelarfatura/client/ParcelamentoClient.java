package com.marcoscoutozup.fatura.parcelarfatura.client;

import com.marcoscoutozup.fatura.parcelarfatura.enums.StatusParcelamento;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(value = "Parcelamento", url = "${host.cartoes.url}", fallback = ParcelamentoClient.ParcelamentoClientFallback.class)
public interface ParcelamentoClient {

    @PostMapping("/api/cartoes/{numeroDoCartao}/parcelas")
    ParcelamentoDaFaturaResponseClient comunicarParcelamentoDeFatura(@PathVariable UUID numeroDoCartao, @RequestBody ParcelamentoDaFaturaRequestClient request);

    @Component
    class ParcelamentoClientFallback implements ParcelamentoClient {

        @Override
        public ParcelamentoDaFaturaResponseClient comunicarParcelamentoDeFatura(UUID numeroDoCartao, ParcelamentoDaFaturaRequestClient request) {
            ParcelamentoDaFaturaResponseClient responseClient = new ParcelamentoDaFaturaResponseClient();
            responseClient.setResultado(StatusParcelamento.NEGADO);
            return responseClient;
        }
    }
}
