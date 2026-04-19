package com.sptech.school;

public enum env {
    BASEURL("INSERIRurl"),
    EMAIL("INSERIRemail"),
    APITOKEN("INSERIRtoken"),;

    private final String valor;

    env(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
