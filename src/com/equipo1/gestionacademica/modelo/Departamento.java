package com.equipo1.gestionacademica.modelo;

import com.equipo1.gestionacademica.estructuras.ListaProfesores;
import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;

public class Departamento {

    private String nombre;
    private ListaProfesores profesores;

    public Departamento(String nombre) {
        this.profesores = new ListaProfesores();
        setNombre(nombre);
    }

    private void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatoInvalidoException(
                "El nombre del departamento no puede ser vacío."
            );
        }

        this.nombre = nombre.trim();
    }

    public String getNombre() {
        return nombre;
    }

    public ListaProfesores getProfesores() {
        return profesores;
    }

    public void agregarProfesor(Profesor profesor) {
        profesores.insertarOrdenado(profesor);
    }

    public Profesor buscarProfesor(String nombre) {
        return profesores.buscar(nombre);
    }

    public void eliminarProfesor(String nombre) {
        profesores.eliminar(nombre);
    }

    public int contarProfesoresDestacados() {
        return profesores.contarProfesoresDestacados();
    }

    public int getTotalProfesores() {
        return profesores.getTotal();
    }

    public String listarProfesores() {
        return profesores.listar();
    }

    @Override
    public String toString() {
        return nombre + " | Profesores: " + getTotalProfesores();
    }
}
