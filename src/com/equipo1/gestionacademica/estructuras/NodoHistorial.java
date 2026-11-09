package com.equipo1.gestionacademica.estructuras;

import com.equipo1.gestionacademica.modelo.RegistroHistorial;
import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;

public class NodoHistorial {

    private RegistroHistorial dato;
    private NodoHistorial siguiente;

    public NodoHistorial(RegistroHistorial dato) {
        if (dato == null) {
            throw new DatoInvalidoException(
                "El registro de historial no puede ser nulo."
            );
        }

        this.dato = dato;
        this.siguiente = null;
    }

    public RegistroHistorial getDato() {
        return dato;
    }

    public void setDato(RegistroHistorial dato) {
        this.dato = dato;
    }

    public NodoHistorial getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoHistorial siguiente) {
        this.siguiente = siguiente;
    }
}