package com.example.tabuas.helper;

public enum TiposCategorias {

    TABUA("TÁBUA"),
    METRO_CUBICO("METRO CÚBICO"),
    TORA("TORA");

    private String valor;

    TiposCategorias(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}
