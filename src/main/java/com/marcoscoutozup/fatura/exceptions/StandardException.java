package com.marcoscoutozup.fatura.exceptions;

import java.util.List;

public class StandardException {

    private Integer status;
    private List<String> mensagens;

    public StandardException(Integer status, List<String> mensagens) {
        this.status = status;
        this.mensagens = mensagens;
    }

    public Integer getStatus() {
        return status;
    }

    public List<String> getMensagens() {
        return mensagens;
    }
}
