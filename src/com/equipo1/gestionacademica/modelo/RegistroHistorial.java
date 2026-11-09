package com.equipo1.gestionacademica.modelo;

import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;

public class RegistroHistorial {

    public static final String CATEGORIA_DOCENTE = "CATEGORIA_DOCENTE";
    public static final String GRADO_CIENTIFICO = "GRADO_CIENTIFICO";
    public static final String EDAD = "EDAD";
    public static final String EVALUACION = "EVALUACION";
    public static final String HORAS_ASIGNATURA = "HORAS_ASIGNATURA";

    private int turno;
    private String descripcion;
    private String nombreDepartamento;
    private String nombreProfesor;
    private String nombreAsignatura;
    private String tipoModificacion;
    private String valorAnterior;
    private String valorNuevo;

    public RegistroHistorial(
        int turno,
        String descripcion,
        String nombreDepartamento,
        String nombreProfesor,
        String nombreAsignatura,
        String tipoModificacion,
        String valorAnterior,
        String valorNuevo
    ) {
        setTurno(turno);
        setDescripcion(descripcion);
        setNombreDepartamento(nombreDepartamento);
        setNombreProfesor(nombreProfesor);
        setNombreAsignatura(nombreAsignatura);
        setTipoModificacion(tipoModificacion);
        setValorAnterior(valorAnterior);
        setValorNuevo(valorNuevo);
    }

    private void setTurno(int turno) {
        if (turno <= 0) {
            throw new DatoInvalidoException(
                "El turno del historial debe ser mayor que 0."
            );
        }

        this.turno = turno;
    }

    private void setDescripcion(String descripcion) {
        this.descripcion = validarTextoObligatorio(
            descripcion,
            "La descripción del historial no puede ser vacía."
        );
    }

    private void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = validarTextoObligatorio(
            nombreDepartamento,
            "El nombre del departamento en el historial no puede ser vacío."
        );
    }

    private void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = validarTextoObligatorio(
            nombreProfesor,
            "El nombre del profesor en el historial no puede ser vacío."
        );
    }

    private void setNombreAsignatura(String nombreAsignatura) {
        if (nombreAsignatura == null) {
            this.nombreAsignatura = null;
            return;
        }

        if (nombreAsignatura.trim().isEmpty()) {
            throw new DatoInvalidoException(
                "El nombre de la asignatura no puede ser vacío si se proporciona."
            );
        }

        this.nombreAsignatura = nombreAsignatura.trim();
    }

    private void setTipoModificacion(String tipoModificacion) {
        this.tipoModificacion = validarTextoObligatorio(
            tipoModificacion,
            "El tipo de modificación no puede ser vacío."
        );
    }

    private void setValorAnterior(String valorAnterior) {
        this.valorAnterior = validarTextoObligatorio(
            valorAnterior,
            "El valor anterior no puede ser vacío."
        );
    }

    private void setValorNuevo(String valorNuevo) {
        this.valorNuevo = validarTextoObligatorio(
            valorNuevo,
            "El valor nuevo no puede ser vacío."
        );
    }

    private String validarTextoObligatorio(String texto, String mensaje) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new DatoInvalidoException(mensaje);
        }

        return texto.trim();
    }

    public int getTurno() {
        return turno;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getNombreDepartamento() {
        return nombreDepartamento;
    }

    public String getNombreProfesor() {
        return nombreProfesor;
    }

    public String getNombreAsignatura() {
        return nombreAsignatura;
    }

    public String getTipoModificacion() {
        return tipoModificacion;
    }

    public String getValorAnterior() {
        return valorAnterior;
    }

    public String getValorNuevo() {
        return valorNuevo;
    }

    @Override
    public String toString() {
        String base = "Turno: " + turno
            + " | " + tipoModificacion
            + " | " + descripcion
            + " | Departamento: " + nombreDepartamento
            + " | Profesor: " + nombreProfesor;

        if (nombreAsignatura != null) {
            base += " | Asignatura: " + nombreAsignatura;
        }

        base += " | Antes: " + valorAnterior
            + " | Después: " + valorNuevo;

        return base;
    }
}