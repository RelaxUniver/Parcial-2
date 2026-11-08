package com.equipo1.gestionacademica.estructuras;

import com.equipo1.gestionacademica.modelo.Profesor;
import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;
import com.equipo1.gestionacademica.excepciones.ElementoDuplicadoException;
import com.equipo1.gestionacademica.excepciones.ElementoNoEncontradoException;

public class ListaProfesores {

    private NodoProfesor cabeza;
    private int total;

    public ListaProfesores() {
        this.cabeza = null;
        this.total = 0;
    }

    public int getTotal() {
        return total;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public void insertarOrdenado(Profesor profesor) {
        if (profesor == null) {
            throw new DatoInvalidoException(
                "El profesor no puede ser nulo."
            );
        }

        if (existe(profesor.getNombre())) {
            throw new ElementoDuplicadoException(
                "El profesor \"" + profesor.getNombre() + "\" ya existe."
            );
        }

        NodoProfesor nuevoNodo = new NodoProfesor(profesor);
        cabeza = insertarOrdenadoRec(cabeza, nuevoNodo);
        total++;
    }

    private NodoProfesor insertarOrdenadoRec(
        NodoProfesor nodo,
        NodoProfesor nuevoNodo
    ) {
        if (nodo == null) {
            return nuevoNodo;
        }

        int comparacion = nuevoNodo
            .getDato()
            .getNombre()
            .compareToIgnoreCase(nodo.getDato().getNombre());

        if (comparacion < 0) {
            nuevoNodo.setSiguiente(nodo);
            return nuevoNodo;
        }

        nodo.setSiguiente(
            insertarOrdenadoRec(nodo.getSiguiente(), nuevoNodo)
        );

        return nodo;
    }

    public boolean existe(String nombre) {
        String nombreValido = validarNombre(nombre);
        return buscarRec(cabeza, nombreValido) != null;
    }

    public Profesor buscar(String nombre) {
        String nombreValido = validarNombre(nombre);

        NodoProfesor nodo = buscarRec(cabeza, nombreValido);

        if (nodo == null) {
            throw new ElementoNoEncontradoException(
                "El profesor \"" + nombreValido + "\" no fue encontrado."
            );
        }

        return nodo.getDato();
    }

    private NodoProfesor buscarRec(NodoProfesor nodo, String nombre) {
        if (nodo == null) {
            return null;
        }

        if (nodo.getDato().getNombre().equalsIgnoreCase(nombre)) {
            return nodo;
        }

        return buscarRec(nodo.getSiguiente(), nombre);
    }

    public void eliminar(String nombre) {
        String nombreValido = validarNombre(nombre);

        if (!existe(nombreValido)) {
            throw new ElementoNoEncontradoException(
                "El profesor \"" + nombreValido + "\" no fue encontrado."
            );
        }

        cabeza = eliminarRec(cabeza, nombreValido);
        total--;
    }

    private NodoProfesor eliminarRec(NodoProfesor nodo, String nombre) {
        if (nodo == null) {
            return null;
        }

        if (nodo.getDato().getNombre().equalsIgnoreCase(nombre)) {
            return nodo.getSiguiente();
        }

        nodo.setSiguiente(
            eliminarRec(nodo.getSiguiente(), nombre)
        );

        return nodo;
    }

    public int contarProfesoresDestacados() {
        return contarProfesoresDestacadosRec(cabeza);
    }

    private int contarProfesoresDestacadosRec(NodoProfesor nodo) {
        if (nodo == null) {
            return 0;
        }

        int contador = 0;

        if (nodo.getDato().esDestacado()) {
            contador = 1;
        }

        return contador + contarProfesoresDestacadosRec(nodo.getSiguiente());
    }

    public String listar() {
        if (estaVacia()) {
            return "No hay profesores registrados.";
        }

        return listarRec(cabeza).trim();
    }

    private String listarRec(NodoProfesor nodo) {
        if (nodo == null) {
            return "";
        }

        return nodo.getDato().toString()
            + "\n"
            + listarRec(nodo.getSiguiente());
    }

    private String validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new DatoInvalidoException(
                "El nombre no puede ser vacío."
            );
        }

        return nombre.trim();
    }
}
