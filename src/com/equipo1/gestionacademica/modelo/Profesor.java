package com.equipo1.gestionacademica.modelo;

import com.equipo1.gestionacademica.estructuras.ListaAsignaturas;
import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;

public class Profesor {

    private String nombre;
    private String categoriaDocente;
    private String gradoCientifico;
    private int edad;
    private int evaluacion;

    private ListaAsignaturas asignaturas;

    public Profesor(
        String nombre,
        String categoriaDocente,
        String gradoCientifico,
        int edad,
        int evaluacion
    ) {
        this.asignaturas = new ListaAsignaturas();

        setNombre(nombre);
        setCategoriaDocente(categoriaDocente);
        setGradoCientifico(gradoCientifico);
        setEdad(edad);
        setEvaluacion(evaluacion);
    }

    private void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatoInvalidoException(
                "El nombre del profesor no puede ser vacío."
            );
        }

        this.nombre = nombre.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public void setCategoriaDocente(String categoriaDocente) {
        if (categoriaDocente == null || categoriaDocente.trim().isEmpty()) {
            throw new DatoInvalidoException(
                "La categoría docente no puede ser vacía."
            );
        }

        this.categoriaDocente = categoriaDocente.trim();
    }

    public String getCategoriaDocente() {
        return categoriaDocente;
    }

    public void setGradoCientifico(String gradoCientifico) {
        if (gradoCientifico == null || gradoCientifico.trim().isEmpty()) {
            throw new DatoInvalidoException(
                "El grado científico no puede ser vacío."
            );
        }

        this.gradoCientifico = gradoCientifico.trim();
    }

    public String getGradoCientifico() {
        return gradoCientifico;
    }

    public void setEdad(int edad) {
        if (edad < 0) {
            throw new DatoInvalidoException(
                "La edad del profesor no puede ser negativa."
            );
        }

        this.edad = edad;
    }

    public int getEdad() {
        return edad;
    }

    public void setEvaluacion(int evaluacion) {
        if (evaluacion < 0 || evaluacion > 100) {
            throw new DatoInvalidoException(
                "La evaluación del profesor debe estar entre 0 y 100."
            );
        }

        this.evaluacion = evaluacion;
    }

    public int getEvaluacion() {
        return evaluacion;
    }

    public void agregarAsignatura(Asignatura asignatura) {
        asignaturas.insertarOrdenado(asignatura);
    }

    public ListaAsignaturas getAsignaturas() {
        return asignaturas;
    }

    public int getTotalHoras() {
        return asignaturas.sumarHoras();
    }

    public boolean esDestacado() {
        return getTotalHoras() > 120;
    }

    @Override
    public String toString() {
        return nombre
            + " | "
            + categoriaDocente
            + " | "
            + gradoCientifico
            + " | Edad: "
            + edad
            + " | Evaluación: "
            + evaluacion
            + " | Horas: "
            + getTotalHoras();
    }
}
