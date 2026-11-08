package com.equipo1.gestionacademica.estructuras;

import com.equipo1.gestionacademica.modelo.Departamento;
import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;
import com.equipo1.gestionacademica.excepciones.ElementoDuplicadoException;
import com.equipo1.gestionacademica.excepciones.ElementoNoEncontradoException;

public class ListaDepartamentos {

    private NodoDepartamento cabeza;
    private int total;

    public ListaDepartamentos() {
        this.cabeza = null;
        this.total = 0;
    }

    public int getTotal() {
        return total;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public void insertarFinal(Departamento departamento) {
        if (departamento == null) {
            throw new DatoInvalidoException(
                "El departamento no puede ser nulo."
            );
        }

        if (existe(departamento.getNombre())) {
            throw new ElementoDuplicadoException(
                "El departamento \"" + departamento.getNombre() + "\" ya existe."
            );
        }

        NodoDepartamento nuevoNodo = new NodoDepartamento(departamento);
        cabeza = insertarFinalRec(cabeza, nuevoNodo);
        total++;
    }

    private NodoDepartamento insertarFinalRec(
        NodoDepartamento nodo,
        NodoDepartamento nuevoNodo
    ) {
        if (nodo == null) {
            return nuevoNodo;
        }

        nodo.setSiguiente(
            insertarFinalRec(nodo.getSiguiente(), nuevoNodo)
        );

        return nodo;
    }

    public boolean existe(String nombre) {
        String nombreValido = validarNombre(nombre);
        return buscarRec(cabeza, nombreValido) != null;
    }

    public Departamento buscar(String nombre) {
        String nombreValido = validarNombre(nombre);

        NodoDepartamento nodo = buscarRec(cabeza, nombreValido);

        if (nodo == null) {
            throw new ElementoNoEncontradoException(
                "El departamento \"" + nombreValido + "\" no fue encontrado."
            );
        }

        return nodo.getDato();
    }

    private NodoDepartamento buscarRec(NodoDepartamento nodo, String nombre) {
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
                "El departamento \"" + nombreValido + "\" no fue encontrado."
            );
        }

        cabeza = eliminarRec(cabeza, nombreValido);
        total--;
    }

    private NodoDepartamento eliminarRec(NodoDepartamento nodo, String nombre) {
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

    private int contarProfesoresDestacadosRec(NodoDepartamento nodo) {
        if (nodo == null) {
            return 0;
        }

        return nodo.getDato().contarProfesoresDestacados()
            + contarProfesoresDestacadosRec(nodo.getSiguiente());
    }

    public String listar() {
        if (estaVacia()) {
            return "No hay departamentos registrados.";
        }

        return listarRec(cabeza).trim();
    }

    private String listarRec(NodoDepartamento nodo) {
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
