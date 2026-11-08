package com.equipo1.gestionacademica.estructuras;

import com.equipo1.gestionacademica.modelo.Departamento;
import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;

public class NodoDepartamento {

    private Departamento dato;
    private NodoDepartamento siguiente;

    public NodoDepartamento(Departamento dato) {
        if (dato == null) {
            throw new DatoInvalidoException(
                "El dato del departamento no puede ser nulo."
            );
        }

        this.dato = dato;
        this.siguiente = null;
    }

    public Departamento getDato() {
        return dato;
    }

    public void setDato(Departamento dato) {
        this.dato = dato;
    }

    public NodoDepartamento getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoDepartamento siguiente) {
        this.siguiente = siguiente;
    }
}
