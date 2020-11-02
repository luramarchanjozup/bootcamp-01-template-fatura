package com.marcoscoutozup.fatura.cartao;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(value = "Cartao", url = "${host.cartoes.url}", decode404 = true)
public interface CartaoClient {

    @GetMapping("/api/cartoes/{id}")
    ResponseEntity<LimiteResponse> consultarlimiteDoCartao(@PathVariable UUID id);

}
