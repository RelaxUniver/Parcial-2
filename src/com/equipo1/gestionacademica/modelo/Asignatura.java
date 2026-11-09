package com.equipo1.gestionacademica.modelo;

import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;

public class Asignatura {

    private String nombre;
    private int horas;

    public Asignatura(String nombre, int horas) {
        setNombre(nombre);
        setHoras(horas);
    }

    private void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatoInvalidoException(
                "El nombre de la asignatura no puede ser vacío."
            );
        }

        this.nombre = nombre.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setHoras(int horas) {
        if (horas <= 0) {
            throw new DatoInvalidoException(
                "Las horas de la asignatura deben ser mayores que 0."
            );
        }

        this.horas = horas;
    }

    public int getHoras() {
        return horas;
    }

    @Override
    public String toString() {
        return nombre + " (" + horas + " horas)";
    }
}
