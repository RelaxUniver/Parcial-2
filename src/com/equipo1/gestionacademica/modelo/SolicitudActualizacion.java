package com.equipo1.gestionacademica.modelo;

import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;

public class SolicitudActualizacion {

    public static final String PENDIENTE = "PENDIENTE";
    public static final String PROCESADA = "PROCESADA";
    public static final String RECHAZADA = "RECHAZADA";

    private int id;
    private String nombreDepartamento;
    private String nombreProfesor;
    private String nombreAsignatura;
    private String tipoActualizacion;
    private String nuevoValor;
    private String estado;

    public SolicitudActualizacion(
        int id,
        String nombreDepartamento,
        String nombreProfesor,
        String nombreAsignatura,
        String tipoActualizacion,
        String nuevoValor
    ) {
        this.estado = PENDIENTE;

        setId(id);
        setNombreDepartamento(nombreDepartamento);
        setNombreProfesor(nombreProfesor);
        setTipoActualizacion(tipoActualizacion);
        setNombreAsignatura(nombreAsignatura);
        setNuevoValor(nuevoValor);
    }

    private void setId(int id) {
        if (id <= 0) {
            throw new DatoInvalidoException(
                "El identificador de la solicitud debe ser mayor que 0."
            );
        }

        this.id = id;
    }

    private void setNombreDepartamento(String nombreDepartamento) {
        this.nombreDepartamento = validarTextoObligatorio(
            nombreDepartamento,
            "El nombre del departamento en la solicitud no puede ser vacío."
        );
    }

    private void setNombreProfesor(String nombreProfesor) {
        this.nombreProfesor = validarTextoObligatorio(
            nombreProfesor,
            "El nombre del profesor en la solicitud no puede ser vacío."
        );
    }

    private void setTipoActualizacion(String tipoActualizacion) {
        String tipo = validarTextoObligatorio(
            tipoActualizacion,
            "El tipo de actualización no puede ser vacío."
        );

        if (
            !tipo.equals(RegistroHistorial.CATEGORIA_DOCENTE)
            && !tipo.equals(RegistroHistorial.GRADO_CIENTIFICO)
            && !tipo.equals(RegistroHistorial.EDAD)
            && !tipo.equals(RegistroHistorial.EVALUACION)
            && !tipo.equals(RegistroHistorial.HORAS_ASIGNATURA)
        ) {
            throw new DatoInvalidoException(
                "El tipo de actualización no es válido."
            );
        }

        this.tipoActualizacion = tipo;
    }

    private void setNombreAsignatura(String nombreAsignatura) {
        if (RegistroHistorial.HORAS_ASIGNATURA.equals(tipoActualizacion)) {
            this.nombreAsignatura = validarTextoObligatorio(
                nombreAsignatura,
                "Las solicitudes de horas de asignatura deben indicar la asignatura."
            );
        } else {
            if (nombreAsignatura != null && !nombreAsignatura.trim().isEmpty()) {
                throw new DatoInvalidoException(
                    "El nombre de la asignatura solo se usa para solicitudes de horas de asignatura."
                );
            }

            this.nombreAsignatura = null;
        }
    }

    private void setNuevoValor(String nuevoValor) {
        String valor = validarTextoObligatorio(
            nuevoValor,
            "El nuevo valor de la solicitud no puede ser vacío."
        );

        if (RegistroHistorial.EDAD.equals(tipoActualizacion)) {
            try {
                int edad = Integer.parseInt(valor);

                if (edad < 0) {
                    throw new DatoInvalidoException(
                        "La nueva edad no puede ser negativa."
                    );
                }

            } catch (NumberFormatException e) {
                throw new DatoInvalidoException(
                    "La nueva edad debe ser un número entero."
                );
            }
        }

        if (RegistroHistorial.EVALUACION.equals(tipoActualizacion)) {
            try {
                int evaluacion = Integer.parseInt(valor);

                if (evaluacion < 0 || evaluacion > 100) {
                    throw new DatoInvalidoException(
                        "La nueva evaluación debe estar entre 0 y 100."
                    );
                }

            } catch (NumberFormatException e) {
                throw new DatoInvalidoException(
                    "La nueva evaluación debe ser un número entero."
                );
            }
        }

        if (RegistroHistorial.HORAS_ASIGNATURA.equals(tipoActualizacion)) {
            try {
                int horas = Integer.parseInt(valor);

                if (horas <= 0) {
                    throw new DatoInvalidoException(
                        "Las nuevas horas deben ser mayores que 0."
                    );
                }

            } catch (NumberFormatException e) {
                throw new DatoInvalidoException(
                    "Las nuevas horas deben ser un número entero."
                );
            }
        }

        this.nuevoValor = valor;
    }

    public void procesar() {
        validarPendiente();
        this.estado = PROCESADA;
    }

    public void rechazar() {
        validarPendiente();
        this.estado = RECHAZADA;
    }

    private void validarPendiente() {
        if (!PENDIENTE.equals(estado)) {
            throw new DatoInvalidoException(
                "La solicitud ya fue atendida y no puede cambiar de estado."
            );
        }
    }

    private String validarTextoObligatorio(String texto, String mensaje) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new DatoInvalidoException(mensaje);
        }

        return texto.trim();
    }

    public int getId() {
        return id;
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

    public String getTipoActualizacion() {
        return tipoActualizacion;
    }

    public String getNuevoValor() {
        return nuevoValor;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        String base = "ID: " + id
            + " | Estado: " + estado
            + " | " + tipoActualizacion
            + " | Departamento: " + nombreDepartamento
            + " | Profesor: " + nombreProfesor;

        if (nombreAsignatura != null) {
            base += " | Asignatura: " + nombreAsignatura;
        }

        base += " | Nuevo valor: " + nuevoValor;

        return base;
    }
}