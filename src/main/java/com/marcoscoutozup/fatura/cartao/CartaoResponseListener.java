package com.marcoscoutozup.fatura.cartao;

import java.util.UUID;

public class CartaoResponseListener {

    private UUID id;
    private String email;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "CartaoResponseListener{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
