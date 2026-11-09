package com.equipo1.gestionacademica;

import com.equipo1.gestionacademica.estructuras.*;
import com.equipo1.gestionacademica.modelo.*;
import com.equipo1.gestionacademica.excepciones.*;

public class SistemaGestionAcademica {

    private ListaDepartamentos departamentos;
    private PilaHistorial historial;
    private ColaSolicitudes solicitudes;

    private int contadorOperaciones;
    private int contadorSolicitudes;

    public SistemaGestionAcademica() {
        this.departamentos = new ListaDepartamentos();
        this.historial = new PilaHistorial();
        this.solicitudes = new ColaSolicitudes();
        this.contadorOperaciones = 0;
        this.contadorSolicitudes = 0;
    }

    public int getTotalDepartamentos() {
        return departamentos.getTotal();
    }

    public int getTotalHistorial() {
        return historial.getTotal();
    }

    public int getTotalSolicitudes() {
        return solicitudes.getTotal();
    }

    public int contarProfesoresDestacados() {
        return departamentos.contarProfesoresDestacados();
    }

    public String listarDepartamentos() {
        return departamentos.listar();
    }

    public String listarProfesores(String nombreDepartamento) {
        Departamento departamento = buscarDepartamento(nombreDepartamento);
        return departamento.listarProfesores();
    }

    public String listarAsignaturas(
        String nombreDepartamento,
        String nombreProfesor
    ) {
        Profesor profesor = buscarProfesor(
            nombreDepartamento,
            nombreProfesor
        );

        return profesor.getAsignaturas().listar();
    }

    public String listarHistorial() {
        return historial.listar();
    }

    public String listarSolicitudes() {
        return solicitudes.listar();
    }

    public void crearDepartamento(String nombre) {
        Departamento departamento = new Departamento(nombre);
        departamentos.insertarFinal(departamento);
    }

    public void crearProfesor(
        String nombreDepartamento,
        String nombreProfesor,
        String categoriaDocente,
        String gradoCientifico,
        int edad,
        int evaluacion
    ) {
        Departamento departamento = buscarDepartamento(nombreDepartamento);

        Profesor profesor = new Profesor(
            nombreProfesor,
            categoriaDocente,
            gradoCientifico,
            edad,
            evaluacion
        );

        departamento.agregarProfesor(profesor);
    }

    public void crearAsignatura(
        String nombreDepartamento,
        String nombreProfesor,
        String nombreAsignatura,
        int horas
    ) {
        Profesor profesor = buscarProfesor(
            nombreDepartamento,
            nombreProfesor
        );

        Asignatura asignatura = new Asignatura(nombreAsignatura, horas);

        profesor.agregarAsignatura(asignatura);
    }

    public Departamento buscarDepartamento(String nombreDepartamento) {
        return departamentos.buscar(nombreDepartamento);
    }

    public Profesor buscarProfesor(
        String nombreDepartamento,
        String nombreProfesor
    ) {
        Departamento departamento = buscarDepartamento(nombreDepartamento);
        return departamento.buscarProfesor(nombreProfesor);
    }

    public Asignatura buscarAsignatura(
        String nombreDepartamento,
        String nombreProfesor,
        String nombreAsignatura
    ) {
        Profesor profesor = buscarProfesor(
            nombreDepartamento,
            nombreProfesor
        );

        return profesor.getAsignaturas().buscar(nombreAsignatura);
    }

    public void eliminarDepartamento(String nombreDepartamento) {
        departamentos.eliminar(nombreDepartamento);
    }

    public void eliminarProfesor(
        String nombreDepartamento,
        String nombreProfesor
    ) {
        Departamento departamento = buscarDepartamento(nombreDepartamento);
        departamento.eliminarProfesor(nombreProfesor);
    }

    public void eliminarAsignatura(
        String nombreDepartamento,
        String nombreProfesor,
        String nombreAsignatura
    ) {
        Profesor profesor = buscarProfesor(
            nombreDepartamento,
            nombreProfesor
        );

        profesor.getAsignaturas().eliminar(nombreAsignatura);
    }

    public void actualizarProfesor(
        String nombreDepartamento,
        String nombreProfesor,
        String tipoActualizacion,
        String nuevoValor
    ) {
        actualizarProfesorInterno(
            nombreDepartamento,
            nombreProfesor,
            tipoActualizacion,
            nuevoValor,
            "Actualización directa de profesor"
        );
    }

    public void actualizarHorasAsignatura(
        String nombreDepartamento,
        String nombreProfesor,
        String nombreAsignatura,
        int nuevasHoras
    ) {
        actualizarHorasAsignaturaInterno(
            nombreDepartamento,
            nombreProfesor,
            nombreAsignatura,
            nuevasHoras,
            "Actualización directa de horas de asignatura"
        );
    }

    public void registrarSolicitud(
        String nombreDepartamento,
        String nombreProfesor,
        String nombreAsignatura,
        String tipoActualizacion,
        String nuevoValor
    ) {
        SolicitudActualizacion solicitud = new SolicitudActualizacion(
            contadorSolicitudes + 1,
            nombreDepartamento,
            nombreProfesor,
            nombreAsignatura,
            tipoActualizacion,
            nuevoValor
        );

        contadorSolicitudes++;
        solicitudes.enqueue(solicitud);
    }

    public SolicitudActualizacion procesarSiguienteSolicitud() {
        if (solicitudes.estaVacia()) {
            throw new EstructuraVaciaException(
                "La cola de solicitudes está vacía. No hay solicitudes para procesar."
            );
        }

        SolicitudActualizacion solicitud = solicitudes.dequeue();

        try {
            aplicarSolicitud(solicitud);
            solicitud.procesar();
        } catch (RuntimeException e) {
            solicitud.rechazar();
        }

        return solicitud;
    }

    public void deshacerUltimoCambio() {
        RegistroHistorial registro = historial.peek();

        if (
            RegistroHistorial.HORAS_ASIGNATURA
                .equals(registro.getTipoModificacion())
        ) {
            Profesor profesor = buscarProfesor(
                registro.getNombreDepartamento(),
                registro.getNombreProfesor()
            );

            Asignatura asignatura = profesor
                .getAsignaturas()
                .buscar(registro.getNombreAsignatura());

            asignatura.setHoras(
                Integer.parseInt(registro.getValorAnterior())
            );

        } else {
            Profesor profesor = buscarProfesor(
                registro.getNombreDepartamento(),
                registro.getNombreProfesor()
            );

            aplicarValorProfesor(
                profesor,
                registro.getTipoModificacion(),
                registro.getValorAnterior()
            );
        }

        historial.pop();
    }

    public RegistroHistorial verUltimoCambio() {
        return historial.peek();
    }

    private void aplicarSolicitud(SolicitudActualizacion solicitud) {
        if (
            RegistroHistorial.HORAS_ASIGNATURA
                .equals(solicitud.getTipoActualizacion())
        ) {
            try {
                int nuevasHoras = Integer.parseInt(
                    solicitud.getNuevoValor()
                );

                actualizarHorasAsignaturaInterno(
                    solicitud.getNombreDepartamento(),
                    solicitud.getNombreProfesor(),
                    solicitud.getNombreAsignatura(),
                    nuevasHoras,
                    "Actualización por solicitud"
                );

            } catch (NumberFormatException e) {
                throw new DatoInvalidoException(
                    "Las horas de la solicitud no son un número válido."
                );
            }

        } else {
            actualizarProfesorInterno(
                solicitud.getNombreDepartamento(),
                solicitud.getNombreProfesor(),
                solicitud.getTipoActualizacion(),
                solicitud.getNuevoValor(),
                "Actualización por solicitud"
            );
        }
    }

    private void actualizarProfesorInterno(
        String nombreDepartamento,
        String nombreProfesor,
        String tipoActualizacion,
        String nuevoValor,
        String descripcion
    ) {
        validarTipoProfesor(tipoActualizacion);

        Profesor profesor = buscarProfesor(
            nombreDepartamento,
            nombreProfesor
        );

        String valorAnterior = obtenerValorProfesor(
            profesor,
            tipoActualizacion
        );

        aplicarValorProfesor(
            profesor,
            tipoActualizacion,
            nuevoValor
        );

        registrarHistorial(
            nombreDepartamento,
            nombreProfesor,
            null,
            tipoActualizacion,
            valorAnterior,
            nuevoValor,
            descripcion
        );
    }

    private void actualizarHorasAsignaturaInterno(
        String nombreDepartamento,
        String nombreProfesor,
        String nombreAsignatura,
        int nuevasHoras,
        String descripcion
    ) {
        Profesor profesor = buscarProfesor(
            nombreDepartamento,
            nombreProfesor
        );

        Asignatura asignatura = profesor
            .getAsignaturas()
            .buscar(nombreAsignatura);

        int horasAnteriores = asignatura.getHoras();

        asignatura.setHoras(nuevasHoras);

        registrarHistorial(
            nombreDepartamento,
            nombreProfesor,
            nombreAsignatura,
            RegistroHistorial.HORAS_ASIGNATURA,
            String.valueOf(horasAnteriores),
            String.valueOf(nuevasHoras),
            descripcion
        );
    }

    private void validarTipoProfesor(String tipoActualizacion) {
        if (tipoActualizacion == null || tipoActualizacion.trim().isEmpty()) {
            throw new DatoInvalidoException(
                "El tipo de actualización no puede ser vacío."
            );
        }

        if (
            !tipoActualizacion.equals(RegistroHistorial.CATEGORIA_DOCENTE)
            && !tipoActualizacion.equals(RegistroHistorial.GRADO_CIENTIFICO)
            && !tipoActualizacion.equals(RegistroHistorial.EDAD)
            && !tipoActualizacion.equals(RegistroHistorial.EVALUACION)
        ) {
            throw new DatoInvalidoException(
                "El tipo de actualización no es válido para un profesor."
            );
        }
    }

    private String obtenerValorProfesor(
        Profesor profesor,
        String tipoActualizacion
    ) {
        if (
            RegistroHistorial.CATEGORIA_DOCENTE.equals(tipoActualizacion)
        ) {
            return profesor.getCategoriaDocente();
        }

        if (
            RegistroHistorial.GRADO_CIENTIFICO.equals(tipoActualizacion)
        ) {
            return profesor.getGradoCientifico();
        }

        if (RegistroHistorial.EDAD.equals(tipoActualizacion)) {
            return String.valueOf(profesor.getEdad());
        }

        if (RegistroHistorial.EVALUACION.equals(tipoActualizacion)) {
            return String.valueOf(profesor.getEvaluacion());
        }

        throw new DatoInvalidoException(
            "No se puede obtener el valor del tipo de actualización indicado."
        );
    }

    private void aplicarValorProfesor(
        Profesor profesor,
        String tipoActualizacion,
        String valor
    ) {
        if (valor == null || valor.trim().isEmpty()) {
            throw new DatoInvalidoException(
                "El nuevo valor no puede ser vacío."
            );
        }

        String valorLimpio = valor.trim();

        if (
            RegistroHistorial.CATEGORIA_DOCENTE.equals(tipoActualizacion)
        ) {
            profesor.setCategoriaDocente(valorLimpio);
            return;
        }

        if (
            RegistroHistorial.GRADO_CIENTIFICO.equals(tipoActualizacion)
        ) {
            profesor.setGradoCientifico(valorLimpio);
            return;
        }

        if (RegistroHistorial.EDAD.equals(tipoActualizacion)) {
            try {
                int edad = Integer.parseInt(valorLimpio);
                profesor.setEdad(edad);
            } catch (NumberFormatException e) {
                throw new DatoInvalidoException(
                    "La edad debe ser un número entero."
                );
            }

            return;
        }

        if (RegistroHistorial.EVALUACION.equals(tipoActualizacion)) {
            try {
                int evaluacion = Integer.parseInt(valorLimpio);
                profesor.setEvaluacion(evaluacion);
            } catch (NumberFormatException e) {
                throw new DatoInvalidoException(
                    "La evaluación debe ser un número entero."
                );
            }

            return;
        }

        throw new DatoInvalidoException(
            "No se puede aplicar el valor al tipo de actualización indicado."
        );
    }

    private void registrarHistorial(
        String nombreDepartamento,
        String nombreProfesor,
        String nombreAsignatura,
        String tipoModificacion,
        String valorAnterior,
        String valorNuevo,
        String descripcion
    ) {
        contadorOperaciones++;

        RegistroHistorial registro = new RegistroHistorial(
            contadorOperaciones,
            descripcion,
            nombreDepartamento,
            nombreProfesor,
            nombreAsignatura,
            tipoModificacion,
            valorAnterior,
            valorNuevo
        );

        historial.push(registro);
    }
}
