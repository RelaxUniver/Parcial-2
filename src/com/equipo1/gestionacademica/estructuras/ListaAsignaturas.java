package com.equipo1.gestionacademica.estructuras;

import com.equipo1.gestionacademica.modelo.Asignatura;
import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;
import com.equipo1.gestionacademica.excepciones.ElementoDuplicadoException;
import com.equipo1.gestionacademica.excepciones.ElementoNoEncontradoException;

public class ListaAsignaturas {

    private NodoAsignatura cabeza;
    private int total;

    public ListaAsignaturas() {
        this.cabeza = null;
        this.total = 0;
    }

    public int getTotal() {
        return total;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public void insertarOrdenado(Asignatura asignatura) {
        if (asignatura == null) {
            throw new DatoInvalidoException(
                "La asignatura no puede ser nula."
            );
        }

        if (existe(asignatura.getNombre())) {
            throw new ElementoDuplicadoException(
                "La asignatura \"" + asignatura.getNombre() + "\" ya existe."
            );
        }

        NodoAsignatura nuevoNodo = new NodoAsignatura(asignatura);
        cabeza = insertarOrdenadoRec(cabeza, nuevoNodo);
        total++;
    }

    private NodoAsignatura insertarOrdenadoRec(
        NodoAsignatura nodo,
        NodoAsignatura nuevoNodo
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

    public Asignatura buscar(String nombre) {
        String nombreValido = validarNombre(nombre);

        NodoAsignatura nodo = buscarRec(cabeza, nombreValido);

        if (nodo == null) {
            throw new ElementoNoEncontradoException(
                "La asignatura \"" + nombreValido + "\" no fue encontrada."
            );
        }

        return nodo.getDato();
    }

    private NodoAsignatura buscarRec(NodoAsignatura nodo, String nombre) {
        if (nodo == null) {
            return null;
        }

        if (nodo.getDato().getNombre().equalsIgnoreCase(nombre)) {
            return nodo;
        }

        return buscarRec(nodo.getSiguiente(), nombre);
    }

    public void actualizarHoras(String nombre, int nuevasHoras) {
        Asignatura asignatura = buscar(nombre);
        asignatura.setHoras(nuevasHoras);
    }

    public void eliminar(String nombre) {
        String nombreValido = validarNombre(nombre);

        if (!existe(nombreValido)) {
            throw new ElementoNoEncontradoException(
                "La asignatura \"" + nombreValido + "\" no fue encontrada."
            );
        }

        cabeza = eliminarRec(cabeza, nombreValido);
        total--;
    }

    private NodoAsignatura eliminarRec(NodoAsignatura nodo, String nombre) {
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

    public int sumarHoras() {
        return sumarHorasRec(cabeza);
    }

    private int sumarHorasRec(NodoAsignatura nodo) {
        if (nodo == null) {
            return 0;
        }

        return nodo.getDato().getHoras()
            + sumarHorasRec(nodo.getSiguiente());
    }

    public String listar() {
        if (estaVacia()) {
            return "No hay asignaturas registradas.";
        }

        return listarRec(cabeza).trim();
    }

    private String listarRec(NodoAsignatura nodo) {
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
