package com.microservicio.hxtecnologia.application.common;

public enum MensajeError {

    DATOS_OBLIGATORIOS("El campo %s es obligatorio"),
    NOMBRE_DUPLICADO("El nombre está duplicado"),
    LONGITUD_PERMITIDA("Longitud permitida para el campo %s es de %s caracteres");

    private final String mensaje;

    MensajeError(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }

    // Método para formatear el mensaje con argumentos, si es necesario
    public String formato(Object... args) {
        return String.format(mensaje, args);
    }
}
