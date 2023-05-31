package com.gestion.comercial.types;

public enum CostoAdministrativo {
    SEGUROS(1D),
    GARANTIA(0.786D),
    REGISTRO_AUTOMOTOR(0.53D),
    LEGALES(1.7D),
    DOCUMENTACION(0.581D),
    GRABADO_AUTOPARTES(0.383D);

    private final Double porcetaje;

    CostoAdministrativo(Double valor){
        this.porcetaje = valor;
    }
    public Double getPorcentaje() {
        return porcetaje;
    }
}
