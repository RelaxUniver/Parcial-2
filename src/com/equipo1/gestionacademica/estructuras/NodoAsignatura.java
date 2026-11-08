package com.equipo1.gestionacademica.estructuras;

import com.equipo1.gestionacademica.modelo.Asignatura;
import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;

public class NodoAsignatura {

    private Asignatura dato;
    private NodoAsignatura siguiente;

    public NodoAsignatura(Asignatura dato) {
        if (dato == null) {
            throw new DatoInvalidoException(
                "El dato de la asignatura no puede ser nulo."
            );
        }

        this.dato = dato;
        this.siguiente = null;
    }

    public Asignatura getDato() {
        return dato;
    }

    public void setDato(Asignatura dato) {
        this.dato = dato;
    }

    public NodoAsignatura getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoAsignatura siguiente) {
        this.siguiente = siguiente;
    }
}
