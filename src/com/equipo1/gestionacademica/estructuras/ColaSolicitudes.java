package com.equipo1.gestionacademica.estructuras;

import com.equipo1.gestionacademica.modelo.SolicitudActualizacion;
import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;
import com.equipo1.gestionacademica.excepciones.EstructuraVaciaException;

public class ColaSolicitudes {

    private NodoSolicitud frente;
    private NodoSolicitud fondo;
    private int total;

    public ColaSolicitudes() {
        this.frente = null;
        this.fondo = null;
        this.total = 0;
    }

    public int getTotal() {
        return total;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public void enqueue(SolicitudActualizacion solicitud) {
        if (solicitud == null) {
            throw new DatoInvalidoException(
                "La solicitud de actualización no puede ser nula."
            );
        }

        NodoSolicitud nuevoNodo = new NodoSolicitud(solicitud);

        if (estaVacia()) {
            frente = nuevoNodo;
            fondo = nuevoNodo;
        } else {
            fondo.setSiguiente(nuevoNodo);
            fondo = nuevoNodo;
        }

        total++;
    }

    public SolicitudActualizacion dequeue() {
        if (estaVacia()) {
            throw new EstructuraVaciaException(
                "La cola de solicitudes está vacía. No hay solicitudes para atender."
            );
        }

        SolicitudActualizacion solicitud = frente.getDato();
        frente = frente.getSiguiente();

        if (frente == null) {
            fondo = null;
        }

        total--;

        return solicitud;
    }

    public SolicitudActualizacion peek() {
        if (estaVacia()) {
            throw new EstructuraVaciaException(
                "La cola de solicitudes está vacía. No hay solicitudes para consultar."
            );
        }

        return frente.getDato();
    }

    public String listar() {
        if (estaVacia()) {
            return "Cola de solicitudes vacía.";
        }

        return listarRec(frente).trim();
    }

    private String listarRec(NodoSolicitud nodo) {
        if (nodo == null) {
            return "";
        }

        return nodo.getDato().toString()
            + "\n"
            + listarRec(nodo.getSiguiente());
    }
}