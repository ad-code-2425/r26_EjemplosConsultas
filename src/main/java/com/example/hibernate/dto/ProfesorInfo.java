
package com.example.hibernate.dto;

/**
 * Clase auxiliar (DTO) para transportar información parcial
 * de la entidad Profesor.
 *
 * No es una entidad Hibernate.
 * Se utiliza en consultas HQL con el operador new.
 */
public class ProfesorInfo {

    private String nombreCompleto;

    /**
     * Constructor obligatorio para usar en HQL:
     * SELECT new com.example.hibernate.util.ProfesorInfo(...)
     */
    public ProfesorInfo(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    @Override
    public String toString() {
        return "ProfesorInfo{nombreCompleto='" + nombreCompleto + "'}";
    }
}