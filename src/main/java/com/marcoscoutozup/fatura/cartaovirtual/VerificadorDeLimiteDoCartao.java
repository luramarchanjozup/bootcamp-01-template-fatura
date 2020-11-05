package com.marcoscoutozup.fatura.cartaovirtual;

import com.marcoscoutozup.fatura.cartao.client.CartaoClient;
import com.marcoscoutozup.fatura.saldocartao.LimiteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class VerificadorDeLimiteDoCartao {

    private final CartaoClient cartaoClient; //1

    public VerificadorDeLimiteDoCartao(CartaoClient cartaoClient) {
        this.cartaoClient = cartaoClient;
    }

    public boolean limiteSolicitadoDoCartaoVirtualECompativel(UUID numeroDoCartao, BigDecimal limiteSolicitado){

                                //2
        final ResponseEntity<LimiteResponse> response =
                cartaoClient.consultarlimiteDoCartao(numeroDoCartao);

        //3
        if(response.getStatusCode().is2xxSuccessful()){
            return response.getBody().getLimite().compareTo(limiteSolicitado) > 0;
        }

        return false;
    }

}
