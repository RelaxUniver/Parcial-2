package com.equipo1.gestionacademica.estructuras;

import com.equipo1.gestionacademica.modelo.SolicitudActualizacion;
import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;

public class NodoSolicitud {

    private SolicitudActualizacion dato;
    private NodoSolicitud siguiente;

    public NodoSolicitud(SolicitudActualizacion dato) {
        if (dato == null) {
            throw new DatoInvalidoException(
                "La solicitud de actualización no puede ser nula."
            );
        }

        this.dato = dato;
        this.siguiente = null;
    }

    public SolicitudActualizacion getDato() {
        return dato;
    }

    public void setDato(SolicitudActualizacion dato) {
        this.dato = dato;
    }

    public NodoSolicitud getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoSolicitud siguiente) {
        this.siguiente = siguiente;
    }
}