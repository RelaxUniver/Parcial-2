package com.equipo1.gestionacademica.estructuras;

import com.equipo1.gestionacademica.modelo.Profesor;
import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;

public class NodoProfesor {

    private Profesor dato;
    private NodoProfesor siguiente;

    public NodoProfesor(Profesor dato) {
        if (dato == null) {
            throw new DatoInvalidoException(
                "El dato del profesor no puede ser nulo."
            );
        }

        this.dato = dato;
        this.siguiente = null;
    }

    public Profesor getDato() {
        return dato;
    }

    public void setDato(Profesor dato) {
        this.dato = dato;
    }

    public NodoProfesor getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoProfesor siguiente) {
        this.siguiente = siguiente;
    }
}