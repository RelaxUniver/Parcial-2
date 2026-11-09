package com.equipo1.gestionacademica.estructuras;

import com.equipo1.gestionacademica.modelo.RegistroHistorial;
import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;
import com.equipo1.gestionacademica.excepciones.EstructuraVaciaException;

public class PilaHistorial {

    private NodoHistorial cima;
    private int total;

    public PilaHistorial() {
        this.cima = null;
        this.total = 0;
    }

    public int getTotal() {
        return total;
    }

    public boolean estaVacia() {
        return cima == null;
    }

    public void push(RegistroHistorial registro) {
        if (registro == null) {
            throw new DatoInvalidoException(
                "El registro de historial no puede ser nulo."
            );
        }

        NodoHistorial nuevoNodo = new NodoHistorial(registro);
        nuevoNodo.setSiguiente(cima);
        cima = nuevoNodo;
        total++;
    }

    public RegistroHistorial pop() {
        if (estaVacia()) {
            throw new EstructuraVaciaException(
                "La pila de historial está vacía. No hay cambios para deshacer."
            );
        }

        RegistroHistorial registro = cima.getDato();
        cima = cima.getSiguiente();
        total--;

        return registro;
    }

    public RegistroHistorial peek() {
        if (estaVacia()) {
            throw new EstructuraVaciaException(
                "La pila de historial está vacía. No hay cambios para consultar."
            );
        }

        return cima.getDato();
    }

    public String listar() {
        if (estaVacia()) {
            return "Pila de historial vacía.";
        }

        return listarRec(cima).trim();
    }

    private String listarRec(NodoHistorial nodo) {
        if (nodo == null) {
            return "";
        }

        return nodo.getDato().toString()
            + "\n"
            + listarRec(nodo.getSiguiente());
    }
}