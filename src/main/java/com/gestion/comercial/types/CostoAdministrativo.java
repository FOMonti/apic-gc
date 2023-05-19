package com.gestion.comercial.types;

public enum CostoAdministrativo {
    SEGUROS(300d),
    GARANTIA(400d),
    REGISTRO_AUTOMOTOR(500d),
    LEGALES(600d),
    DOCUMENTACION(700d),
    GRABADO_AUTOPARTES(800d);

    private final Double valor;

    CostoAdministrativo(Double valor){
        this.valor = valor;
    }
    public Double getValor() {
        return valor;
    }
}
