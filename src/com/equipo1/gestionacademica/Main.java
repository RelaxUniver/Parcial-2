package com.equipo1.gestionacademica;

import com.equipo1.gestionacademica.estructuras.ListaAsignaturas;
import com.equipo1.gestionacademica.estructuras.ListaProfesores;
import com.equipo1.gestionacademica.estructuras.PilaHistorial;
import com.equipo1.gestionacademica.modelo.Asignatura;
import com.equipo1.gestionacademica.modelo.Profesor;
import com.equipo1.gestionacademica.modelo.Departamento;
import com.equipo1.gestionacademica.modelo.RegistroHistorial;
import com.equipo1.gestionacademica.modelo.SolicitudActualizacion;
import com.equipo1.gestionacademica.excepciones.DatoInvalidoException;
import com.equipo1.gestionacademica.excepciones.ElementoDuplicadoException;
import com.equipo1.gestionacademica.excepciones.ElementoNoEncontradoException;
import com.equipo1.gestionacademica.estructuras.ListaDepartamentos;
import com.equipo1.gestionacademica.excepciones.EstructuraVaciaException;
import com.equipo1.gestionacademica.estructuras.ColaSolicitudes;


public class Main {

    private static int pruebasExitosas = 0;
    private static int pruebasFallidas = 0;

    public static void main(String[] args) {

        System.out.println("====================================");
        System.out.println("PROYECTO EQUIPO 1");
        System.out.println("GESTIÓN ACADÉMICA Y CARGA DOCENTE");
        System.out.println("====================================");

        pruebasAsignaturas();
        pruebasProfesores();
        pruebasDepartamentos();
        pruebasPila();
        pruebasCola();
        pruebasSistema();

        System.out.println();
        System.out.println("====================================");
        System.out.println("RESUMEN GENERAL DE PRUEBAS");
        System.out.println("====================================");
        System.out.println("Pruebas exitosas: " + pruebasExitosas);
        System.out.println("Pruebas fallidas: " + pruebasFallidas);
    }

    private static void pruebasAsignaturas() {

        System.out.println();
        System.out.println("====================================");
        System.out.println("PRUEBAS DE ASIGNATURAS");
        System.out.println("====================================");

        ListaAsignaturas lista = new ListaAsignaturas();

        probar(
                "Insertar asignatura válida",
                null,
                () -> lista.insertarOrdenado(
                        new Asignatura("Estructuras de Datos", 100)));

        probar(
                "Insertar asignatura duplicada",
                ElementoDuplicadoException.class,
                () -> lista.insertarOrdenado(
                        new Asignatura("Estructuras de Datos", 50)));

        probar(
                "Insertar asignatura con horas en cero",
                DatoInvalidoException.class,
                () -> lista.insertarOrdenado(
                        new Asignatura("Matemática", 0)));

        probar(
                "Insertar asignatura con horas negativas",
                DatoInvalidoException.class,
                () -> lista.insertarOrdenado(
                        new Asignatura("Física", -10)));

        probar(
                "Insertar asignatura con nombre vacío",
                DatoInvalidoException.class,
                () -> lista.insertarOrdenado(
                        new Asignatura("   ", 80)));

        probar(
                "Insertar asignatura nula",
                DatoInvalidoException.class,
                () -> lista.insertarOrdenado(null));

        lista.insertarOrdenado(
                new Asignatura("Programación", 80));

        lista.insertarOrdenado(
                new Asignatura("Bases de Datos", 60));

        probar(
                "Buscar asignatura existente",
                null,
                () -> lista.buscar("Programación"));

        probar(
                "Buscar asignatura inexistente",
                ElementoNoEncontradoException.class,
                () -> lista.buscar("Redes de Computadoras"));

        probar(
                "Actualizar horas de asignatura existente",
                null,
                () -> lista.actualizarHoras("Programación", 90));

        probar(
                "Actualizar horas con valor negativo",
                DatoInvalidoException.class,
                () -> lista.actualizarHoras("Programación", -5));

        probar(
                "Actualizar horas de asignatura inexistente",
                ElementoNoEncontradoException.class,
                () -> lista.actualizarHoras("Sistemas Operativos", 40));

        probar(
                "Eliminar asignatura existente",
                null,
                () -> lista.eliminar("Bases de Datos"));

        probar(
                "Eliminar asignatura inexistente",
                ElementoNoEncontradoException.class,
                () -> lista.eliminar("Inteligencia Artificial"));

        probar(
                "Verificar total de asignaturas y horas",
                null,
                () -> {
                    verificar(
                            lista.getTotal() == 2,
                            "Se esperaban 2 asignaturas.");

                    verificar(
                            lista.sumarHoras() == 190,
                            "Se esperaban 190 horas.");
                });

        System.out.println();
        System.out.println("LISTADO ACTUAL DE ASIGNATURAS");
        System.out.println(lista.listar());
    }

    private static void pruebasProfesores() {

        System.out.println();
        System.out.println("====================================");
        System.out.println("PRUEBAS DE PROFESORES");
        System.out.println("====================================");

        ListaProfesores profesores = new ListaProfesores();

        probar(
                "Contar profesores destacados en lista vacía",
                null,
                () -> {
                    verificar(
                            profesores.contarProfesoresDestacados() == 0,
                            "Una lista vacía debería tener 0 profesores destacados.");
                });

        probar(
                "Insertar profesor nulo",
                DatoInvalidoException.class,
                () -> profesores.insertarOrdenado(null));

        probar(
                "Crear profesor con nombre vacío",
                DatoInvalidoException.class,
                () -> new Profesor(
                        "   ",
                        "Titular",
                        "Doctor",
                        40,
                        90));

        probar(
                "Crear profesor con categoría vacía",
                DatoInvalidoException.class,
                () -> new Profesor(
                        "Ana",
                        "   ",
                        "Doctor",
                        40,
                        90));

        probar(
                "Crear profesor con grado científico vacío",
                DatoInvalidoException.class,
                () -> new Profesor(
                        "Ana",
                        "Titular",
                        "   ",
                        40,
                        90));

        probar(
                "Crear profesor con edad negativa",
                DatoInvalidoException.class,
                () -> new Profesor(
                        "Ana",
                        "Titular",
                        "Doctor",
                        -5,
                        90));

        probar(
                "Crear profesor con evaluación negativa",
                DatoInvalidoException.class,
                () -> new Profesor(
                        "Ana",
                        "Titular",
                        "Doctor",
                        40,
                        -1));

        probar(
                "Crear profesor con evaluación mayor que 100",
                DatoInvalidoException.class,
                () -> new Profesor(
                        "Ana",
                        "Titular",
                        "Doctor",
                        40,
                        101));

        probar(
                "Agregar asignatura duplicada a un profesor",
                ElementoDuplicadoException.class,
                () -> {
                    Profesor temporal = new Profesor(
                            "Temporal",
                            "Asistente",
                            "Licenciado",
                            30,
                            80);

                    temporal.agregarAsignatura(
                            new Asignatura("Algoritmos", 10));

                    temporal.agregarAsignatura(
                            new Asignatura("Algoritmos", 20));
                });

        probar(
                "Insertar profesor válido Ana",
                null,
                () -> {
                    Profesor ana = new Profesor(
                            "Ana",
                            "Asistente",
                            "Licenciada",
                            30,
                            80);

                    ana.agregarAsignatura(
                            new Asignatura("Matemática", 100));

                    profesores.insertarOrdenado(ana);
                });

        probar(
                "Insertar profesor válido Bruno",
                null,
                () -> {
                    Profesor bruno = new Profesor(
                            "Bruno",
                            "Titular",
                            "Doctor",
                            45,
                            95);

                    bruno.agregarAsignatura(
                            new Asignatura("Programación", 120));

                    profesores.insertarOrdenado(bruno);
                });

        probar(
                "Insertar profesor válido Carla",
                null,
                () -> {
                    Profesor carla = new Profesor(
                            "Carla",
                            "Auxiliar",
                            "Máster",
                            35,
                            85);

                    carla.agregarAsignatura(
                            new Asignatura("Bases de Datos", 121));

                    profesores.insertarOrdenado(carla);
                });

        probar(
                "Insertar profesor válido Diego",
                null,
                () -> {
                    Profesor diego = new Profesor(
                            "Diego",
                            "Adjunto",
                            "Doctor",
                            50,
                            90);

                    diego.agregarAsignatura(
                            new Asignatura("Redes", 60));

                    diego.agregarAsignatura(
                            new Asignatura("Sistemas Operativos", 70));

                    profesores.insertarOrdenado(diego);
                });

        probar(
                "Insertar profesor duplicado Ana",
                ElementoDuplicadoException.class,
                () -> {
                    Profesor ana = new Profesor(
                            "Ana",
                            "Titular",
                            "Doctora",
                            40,
                            90);

                    profesores.insertarOrdenado(ana);
                });

        probar(
                "Buscar profesor existente",
                null,
                () -> profesores.buscar("Ana"));

        probar(
                "Buscar profesor inexistente",
                ElementoNoEncontradoException.class,
                () -> profesores.buscar("Zoe"));

        probar(
                "Actualizar evaluación válida",
                null,
                () -> {
                    Profesor ana = profesores.buscar("Ana");
                    ana.setEvaluacion(90);

                    verificar(
                            ana.getEvaluacion() == 90,
                            "La evaluación debió actualizarse a 90.");
                });

        probar(
                "Actualizar evaluación inválida",
                DatoInvalidoException.class,
                () -> profesores.buscar("Ana").setEvaluacion(101));

        probar(
                "Actualizar edad negativa",
                DatoInvalidoException.class,
                () -> profesores.buscar("Ana").setEdad(-2));

        probar(
                "Actualizar categoría vacía",
                DatoInvalidoException.class,
                () -> profesores.buscar("Ana").setCategoriaDocente("   "));

        probar(
                "Verificar total de horas de Diego",
                null,
                () -> {
                    Profesor diego = profesores.buscar("Diego");

                    verificar(
                            diego.getTotalHoras() == 130,
                            "Diego debería tener 130 horas.");
                });

        probar(
                "Verificar orden alfabético de profesores",
                null,
                () -> {
                    String listado = profesores.listar();

                    int ana = listado.indexOf("Ana");
                    int bruno = listado.indexOf("Bruno");
                    int carla = listado.indexOf("Carla");
                    int diego = listado.indexOf("Diego");

                    verificar(
                            ana >= 0 && bruno >= 0 && carla >= 0 && diego >= 0,
                            "Faltan profesores en el listado.");

                    verificar(
                            ana < bruno && bruno < carla && carla < diego,
                            "El orden alfabético de profesores no es correcto.");
                });

        probar(
                "Contar profesores destacados iniciales",
                null,
                () -> {
                    verificar(
                            profesores.contarProfesoresDestacados() == 2,
                            "Debería haber 2 profesores destacados inicialmente.");
                });

        probar(
                "Eliminar profesor destacado Carla",
                null,
                () -> profesores.eliminar("Carla"));

        probar(
                "Contar profesores destacados después de eliminar a Carla",
                null,
                () -> {
                    verificar(
                            profesores.contarProfesoresDestacados() == 1,
                            "Debería quedar 1 profesor destacado después de eliminar a Carla.");
                });

        probar(
                "Eliminar profesor destacado Diego",
                null,
                () -> profesores.eliminar("Diego"));

        probar(
                "Contar profesores destacados después de eliminar a Diego",
                null,
                () -> {
                    verificar(
                            profesores.contarProfesoresDestacados() == 0,
                            "No deberían quedar profesores destacados.");
                });

        probar(
                "Eliminar profesor inexistente",
                ElementoNoEncontradoException.class,
                () -> profesores.eliminar("Zoe"));

        System.out.println();
        System.out.println("LISTADO FINAL DE PROFESORES");
        System.out.println(profesores.listar());
        System.out.println("Total de profesores: " + profesores.getTotal());
    }

    private static void verificar(boolean condicion, String mensaje) {
        if (!condicion) {
            throw new RuntimeException(mensaje);
        }
    }

    private static void probar(
            String descripcion,
            Class<? extends RuntimeException> excepcionEsperada,
            Runnable accion) {
        boolean exito;
        String detalle;

        try {
            accion.run();

            if (excepcionEsperada == null) {
                exito = true;
                detalle = "Operación correcta.";
            } else {
                exito = false;
                detalle = "Se esperaba la excepción "
                        + excepcionEsperada.getSimpleName()
                        + " y no se lanzó ninguna excepción.";
            }

        } catch (RuntimeException e) {

            if (excepcionEsperada != null
                    && excepcionEsperada.isInstance(e)) {

                exito = true;
                detalle = "Excepción esperada: "
                        + e.getClass().getSimpleName();

            } else {

                exito = false;
                detalle = "Excepción inesperada: "
                        + e.getClass().getSimpleName()
                        + " - "
                        + e.getMessage();
            }
        }

        if (exito) {
            pruebasExitosas++;
            System.out.println("[EXITOSA] " + descripcion);
        } else {
            pruebasFallidas++;
            System.out.println("[FALLIDA] " + descripcion);
        }

        System.out.println("   Detalle: " + detalle);
    }

    private static void pruebasDepartamentos() {

        System.out.println();
        System.out.println("====================================");
        System.out.println("PRUEBAS DE DEPARTAMENTOS");
        System.out.println("====================================");

        ListaDepartamentos departamentos = new ListaDepartamentos();

        probar(
                "Contar profesores destacados en lista vacía de departamentos",
                null,
                () -> {
                    verificar(
                            departamentos.contarProfesoresDestacados() == 0,
                            "Una lista vacía de departamentos debería tener 0 destacados.");
                });

        probar(
                "Insertar departamento nulo",
                DatoInvalidoException.class,
                () -> departamentos.insertarFinal(null));

        probar(
                "Crear departamento con nombre vacío",
                DatoInvalidoException.class,
                () -> new Departamento("   "));

        probar(
                "Insertar departamento válido Informática",
                null,
                () -> departamentos.insertarFinal(
                        new Departamento("Informática")));

        probar(
                "Insertar departamento válido Matemática",
                null,
                () -> departamentos.insertarFinal(
                        new Departamento("Matemática")));

        probar(
                "Insertar departamento duplicado Informática",
                ElementoDuplicadoException.class,
                () -> departamentos.insertarFinal(
                        new Departamento("Informática")));

        probar(
                "Buscar departamento existente",
                null,
                () -> departamentos.buscar("Informática"));

        probar(
                "Buscar departamento inexistente",
                ElementoNoEncontradoException.class,
                () -> departamentos.buscar("Física"));

        probar(
                "Agregar profesor Ana a Informática",
                null,
                () -> {
                    Departamento departamento = departamentos.buscar("Informática");

                    Profesor ana = new Profesor(
                            "Ana",
                            "Asistente",
                            "Licenciada",
                            30,
                            80);

                    ana.agregarAsignatura(
                            new Asignatura("Matemática", 100));

                    departamento.agregarProfesor(ana);
                });

        probar(
                "Agregar profesor Carla a Informática",
                null,
                () -> {
                    Departamento departamento = departamentos.buscar("Informática");

                    Profesor carla = new Profesor(
                            "Carla",
                            "Auxiliar",
                            "Máster",
                            35,
                            85);

                    carla.agregarAsignatura(
                            new Asignatura("Bases de Datos", 121));

                    departamento.agregarProfesor(carla);
                });

        probar(
                "Agregar profesor Bruno a Matemática",
                null,
                () -> {
                    Departamento departamento = departamentos.buscar("Matemática");

                    Profesor bruno = new Profesor(
                            "Bruno",
                            "Titular",
                            "Doctor",
                            45,
                            95);

                    bruno.agregarAsignatura(
                            new Asignatura("Programación", 120));

                    departamento.agregarProfesor(bruno);
                });

        probar(
                "Agregar profesor Diego a Matemática",
                null,
                () -> {
                    Departamento departamento = departamentos.buscar("Matemática");

                    Profesor diego = new Profesor(
                            "Diego",
                            "Adjunto",
                            "Doctor",
                            50,
                            90);

                    diego.agregarAsignatura(
                            new Asignatura("Redes", 60));

                    diego.agregarAsignatura(
                            new Asignatura("Sistemas Operativos", 70));

                    departamento.agregarProfesor(diego);
                });

        probar(
                "Agregar profesor duplicado Ana en Informática",
                ElementoDuplicadoException.class,
                () -> {
                    Departamento departamento = departamentos.buscar("Informática");

                    Profesor ana = new Profesor(
                            "Ana",
                            "Titular",
                            "Doctora",
                            40,
                            90);

                    departamento.agregarProfesor(ana);
                });

        probar(
                "Buscar profesor en departamento",
                null,
                () -> departamentos.buscar("Informática").buscarProfesor("Ana"));

        probar(
                "Buscar profesor inexistente en departamento",
                ElementoNoEncontradoException.class,
                () -> departamentos.buscar("Informática").buscarProfesor("Zoe"));

        probar(
                "Verificar orden de inserción de departamentos",
                null,
                () -> {
                    String listado = departamentos.listar();

                    int informatica = listado.indexOf("Informática");
                    int matematica = listado.indexOf("Matemática");

                    verificar(
                            informatica >= 0 && matematica >= 0,
                            "Faltan departamentos en el listado.");

                    verificar(
                            informatica < matematica,
                            "Los departamentos deben insertarse al final, manteniendo el orden de inserción.");
                });

        probar(
                "Verificar total de profesores por departamento",
                null,
                () -> {
                    verificar(
                            departamentos.buscar("Informática").getTotalProfesores() == 2,
                            "Informática debería tener 2 profesores.");

                    verificar(
                            departamentos.buscar("Matemática").getTotalProfesores() == 2,
                            "Matemática debería tener 2 profesores.");
                });

        probar(
                "Contar destacados por departamento Informática",
                null,
                () -> {
                    verificar(
                            departamentos.buscar("Informática")
                                    .contarProfesoresDestacados() == 1,
                            "Informática debería tener 1 profesor destacado.");
                });

        probar(
                "Contar destacados por departamento Matemática",
                null,
                () -> {
                    verificar(
                            departamentos.buscar("Matemática")
                                    .contarProfesoresDestacados() == 1,
                            "Matemática debería tener 1 profesor destacado.");
                });

        probar(
                "Contar profesores destacados total en la multilista",
                null,
                () -> {
                    verificar(
                            departamentos.contarProfesoresDestacados() == 2,
                            "La multilista completa debería tener 2 profesores destacados.");
                });

        probar(
                "Eliminar departamento Matemática",
                null,
                () -> departamentos.eliminar("Matemática"));

        probar(
                "Contar profesores destacados después de eliminar Matemática",
                null,
                () -> {
                    verificar(
                            departamentos.contarProfesoresDestacados() == 1,
                            "Debería quedar 1 profesor destacado después de eliminar Matemática.");
                });

        probar(
                "Eliminar departamento inexistente",
                ElementoNoEncontradoException.class,
                () -> departamentos.eliminar("Física"));

        System.out.println();
        System.out.println("LISTADO FINAL DE DEPARTAMENTOS");
        System.out.println(departamentos.listar());
        System.out.println("Total de departamentos: " + departamentos.getTotal());
    }

        private static void pruebasPila() {

        System.out.println();
        System.out.println("====================================");
        System.out.println("PRUEBAS DE PILA DE HISTORIAL");
        System.out.println("====================================");

        PilaHistorial pila = new PilaHistorial();

        probar(
            "Pila inicia vacía",
            null,
            () -> {
                verificar(
                    pila.estaVacia(),
                    "La pila debería iniciar vacía."
                );

                verificar(
                    pila.getTotal() == 0,
                    "La pila debería tener total 0 al iniciar."
                );
            }
        );

        probar(
            "Pop en pila vacía",
            EstructuraVaciaException.class,
            () -> pila.pop()
        );

        probar(
            "Peek en pila vacía",
            EstructuraVaciaException.class,
            () -> pila.peek()
        );

        probar(
            "Push registro nulo",
            DatoInvalidoException.class,
            () -> pila.push(null)
        );

        probar(
            "Crear registro con turno inválido",
            DatoInvalidoException.class,
            () -> new RegistroHistorial(
                0,
                "Cambio de evaluación",
                "Informática",
                "Ana",
                null,
                RegistroHistorial.EVALUACION,
                "80",
                "90"
            )
        );

        probar(
            "Crear registro con descripción vacía",
            DatoInvalidoException.class,
            () -> new RegistroHistorial(
                1,
                "   ",
                "Informática",
                "Ana",
                null,
                RegistroHistorial.EVALUACION,
                "80",
                "90"
            )
        );

        probar(
            "Crear registro con departamento vacío",
            DatoInvalidoException.class,
            () -> new RegistroHistorial(
                1,
                "Cambio de evaluación",
                "   ",
                "Ana",
                null,
                RegistroHistorial.EVALUACION,
                "80",
                "90"
            )
        );

        probar(
            "Crear registro con profesor vacío",
            DatoInvalidoException.class,
            () -> new RegistroHistorial(
                1,
                "Cambio de evaluación",
                "Informática",
                "   ",
                null,
                RegistroHistorial.EVALUACION,
                "80",
                "90"
            )
        );

        probar(
            "Crear registro con tipo de modificación vacío",
            DatoInvalidoException.class,
            () -> new RegistroHistorial(
                1,
                "Cambio de evaluación",
                "Informática",
                "Ana",
                null,
                "   ",
                "80",
                "90"
            )
        );

        probar(
            "Crear registro con valor anterior nulo",
            DatoInvalidoException.class,
            () -> new RegistroHistorial(
                1,
                "Cambio de evaluación",
                "Informática",
                "Ana",
                null,
                RegistroHistorial.EVALUACION,
                null,
                "90"
            )
        );

        probar(
            "Crear registro con valor nuevo nulo",
            DatoInvalidoException.class,
            () -> new RegistroHistorial(
                1,
                "Cambio de evaluación",
                "Informática",
                "Ana",
                null,
                RegistroHistorial.EVALUACION,
                "80",
                null
            )
        );

        probar(
            "Push cambio de evaluación válido",
            null,
            () -> pila.push(
                new RegistroHistorial(
                    1,
                    "Cambio de evaluación",
                    "Informática",
                    "Ana",
                    null,
                    RegistroHistorial.EVALUACION,
                    "80",
                    "90"
                )
            )
        );

        probar(
            "Push cambio de horas de asignatura válido",
            null,
            () -> pila.push(
                new RegistroHistorial(
                    2,
                    "Cambio de horas",
                    "Informática",
                    "Ana",
                    "Matemática",
                    RegistroHistorial.HORAS_ASIGNATURA,
                    "100",
                    "120"
                )
            )
        );

        probar(
            "Verificar total de registros después de push",
            null,
            () -> {
                verificar(
                    pila.getTotal() == 2,
                    "La pila debería tener 2 registros."
                );
            }
        );

        probar(
            "Peek devuelve el último cambio",
            null,
            () -> {
                RegistroHistorial registro = pila.peek();

                verificar(
                    registro.getTurno() == 2,
                    "El último cambio debería ser el turno 2."
                );

                verificar(
                    registro.getTipoModificacion()
                        .equals(RegistroHistorial.HORAS_ASIGNATURA),
                    "El último cambio debería ser de horas de asignatura."
                );
            }
        );

        probar(
            "Peek no modifica el total de la pila",
            null,
            () -> {
                verificar(
                    pila.getTotal() == 2,
                    "Peek no debería modificar el total de la pila."
                );
            }
        );

        probar(
            "Pop devuelve el último cambio",
            null,
            () -> {
                RegistroHistorial registro = pila.pop();

                verificar(
                    registro.getTurno() == 2,
                    "El pop debería devolver el turno 2."
                );

                verificar(
                    registro.getValorAnterior().equals("100"),
                    "El valor anterior debería ser 100."
                );

                verificar(
                    registro.getValorNuevo().equals("120"),
                    "El valor nuevo debería ser 120."
                );
            }
        );

        probar(
            "Verificar total después de primer pop",
            null,
            () -> {
                verificar(
                    pila.getTotal() == 1,
                    "La pila debería tener 1 registro después del primer pop."
                );
            }
        );

        probar(
            "Pop devuelve el cambio anterior",
            null,
            () -> {
                RegistroHistorial registro = pila.pop();

                verificar(
                    registro.getTurno() == 1,
                    "El pop debería devolver el turno 1."
                );

                verificar(
                    registro.getTipoModificacion()
                        .equals(RegistroHistorial.EVALUACION),
                    "El cambio debería ser de evaluación."
                );
            }
        );

        probar(
            "Pila queda vacía después de pops",
            null,
            () -> {
                verificar(
                    pila.estaVacia(),
                    "La pila debería quedar vacía después de dos pops."
                );
            }
        );

        probar(
            "Listar pila vacía",
            null,
            () -> {
                verificar(
                    pila.listar().equals("Pila de historial vacía."),
                    "El listado debería indicar que la pila está vacía."
                );
            }
        );

        probar(
            "Push después de vaciar la pila",
            null,
            () -> pila.push(
                new RegistroHistorial(
                    3,
                    "Cambio de categoría docente",
                    "Informática",
                    "Ana",
                    null,
                    RegistroHistorial.CATEGORIA_DOCENTE,
                    "Asistente",
                    "Titular"
                )
            )
        );

        probar(
            "Listar pila con un registro",
            null,
            () -> {
                String listado = pila.listar();

                verificar(
                    listado.contains("Turno: 3"),
                    "El listado debería contener el turno 3."
                );
            }
        );

        probar(
            "Verificar orden LIFO en listado",
            null,
            () -> {
                PilaHistorial pila2 = new PilaHistorial();

                pila2.push(
                    new RegistroHistorial(
                        1,
                        "Cambio uno",
                        "Informática",
                        "Ana",
                        null,
                        RegistroHistorial.EVALUACION,
                        "80",
                        "85"
                    )
                );

                pila2.push(
                    new RegistroHistorial(
                        2,
                        "Cambio dos",
                        "Informática",
                        "Ana",
                        null,
                        RegistroHistorial.EVALUACION,
                        "85",
                        "90"
                    )
                );

                pila2.push(
                    new RegistroHistorial(
                        3,
                        "Cambio tres",
                        "Informática",
                        "Ana",
                        null,
                        RegistroHistorial.EVALUACION,
                        "90",
                        "95"
                    )
                );

                String listado = pila2.listar();

                int turno1 = listado.indexOf("Turno: 1");
                int turno2 = listado.indexOf("Turno: 2");
                int turno3 = listado.indexOf("Turno: 3");

                verificar(
                    turno1 >= 0 && turno2 >= 0 && turno3 >= 0,
                    "El listado debería contener los tres turnos."
                );

                verificar(
                    turno3 < turno2 && turno2 < turno1,
                    "El listado debería mostrar primero el último cambio registrado."
                );
            }
        );
    }

        private static void pruebasCola() {

        System.out.println();
        System.out.println("====================================");
        System.out.println("PRUEBAS DE COLA DE SOLICITUDES");
        System.out.println("====================================");

        ColaSolicitudes cola = new ColaSolicitudes();

        probar(
            "Cola inicia vacía",
            null,
            () -> {
                verificar(
                    cola.estaVacia(),
                    "La cola debería iniciar vacía."
                );

                verificar(
                    cola.getTotal() == 0,
                    "La cola debería tener total 0 al iniciar."
                );
            }
        );

        probar(
            "Dequeue en cola vacía",
            EstructuraVaciaException.class,
            () -> cola.dequeue()
        );

        probar(
            "Peek en cola vacía",
            EstructuraVaciaException.class,
            () -> cola.peek()
        );

        probar(
            "Encolar solicitud nula",
            DatoInvalidoException.class,
            () -> cola.enqueue(null)
        );

        probar(
            "Crear solicitud con id inválido",
            DatoInvalidoException.class,
            () -> new SolicitudActualizacion(
                0,
                "Informática",
                "Ana",
                null,
                RegistroHistorial.EVALUACION,
                "90"
            )
        );

        probar(
            "Crear solicitud con departamento vacío",
            DatoInvalidoException.class,
            () -> new SolicitudActualizacion(
                1,
                "   ",
                "Ana",
                null,
                RegistroHistorial.EVALUACION,
                "90"
            )
        );

        probar(
            "Crear solicitud con profesor vacío",
            DatoInvalidoException.class,
            () -> new SolicitudActualizacion(
                1,
                "Informática",
                "   ",
                null,
                RegistroHistorial.EVALUACION,
                "90"
            )
        );

        probar(
            "Crear solicitud con tipo vacío",
            DatoInvalidoException.class,
            () -> new SolicitudActualizacion(
                1,
                "Informática",
                "Ana",
                null,
                "   ",
                "90"
            )
        );

        probar(
            "Crear solicitud con tipo inválido",
            DatoInvalidoException.class,
            () -> new SolicitudActualizacion(
                1,
                "Informática",
                "Ana",
                null,
                "TIPO_INVALIDO",
                "90"
            )
        );

        probar(
            "Crear solicitud de horas sin asignatura",
            DatoInvalidoException.class,
            () -> new SolicitudActualizacion(
                1,
                "Informática",
                "Ana",
                null,
                RegistroHistorial.HORAS_ASIGNATURA,
                "100"
            )
        );

        probar(
            "Crear solicitud de evaluación con asignatura",
            DatoInvalidoException.class,
            () -> new SolicitudActualizacion(
                1,
                "Informática",
                "Ana",
                "Matemática",
                RegistroHistorial.EVALUACION,
                "90"
            )
        );

        probar(
            "Crear solicitud con edad no numérica",
            DatoInvalidoException.class,
            () -> new SolicitudActualizacion(
                1,
                "Informática",
                "Ana",
                null,
                RegistroHistorial.EDAD,
                "treinta"
            )
        );

        probar(
            "Crear solicitud con edad negativa",
            DatoInvalidoException.class,
            () -> new SolicitudActualizacion(
                1,
                "Informática",
                "Ana",
                null,
                RegistroHistorial.EDAD,
                "-5"
            )
        );

        probar(
            "Crear solicitud con evaluación inválida",
            DatoInvalidoException.class,
            () -> new SolicitudActualizacion(
                1,
                "Informática",
                "Ana",
                null,
                RegistroHistorial.EVALUACION,
                "101"
            )
        );

        probar(
            "Crear solicitud con horas negativas",
            DatoInvalidoException.class,
            () -> new SolicitudActualizacion(
                1,
                "Informática",
                "Ana",
                "Matemática",
                RegistroHistorial.HORAS_ASIGNATURA,
                "-10"
            )
        );

        probar(
            "Encolar solicitud válida de evaluación",
            null,
            () -> cola.enqueue(
                new SolicitudActualizacion(
                    1,
                    "Informática",
                    "Ana",
                    null,
                    RegistroHistorial.EVALUACION,
                    "90"
                )
            )
        );

        probar(
            "Encolar solicitud válida de horas",
            null,
            () -> cola.enqueue(
                new SolicitudActualizacion(
                    2,
                    "Informática",
                    "Ana",
                    "Matemática",
                    RegistroHistorial.HORAS_ASIGNATURA,
                    "120"
                )
            )
        );

        probar(
            "Verificar total después de encolar",
            null,
            () -> {
                verificar(
                    cola.getTotal() == 2,
                    "La cola debería tener 2 solicitudes."
                );
            }
        );

        probar(
            "Peek devuelve la primera solicitud",
            null,
            () -> {
                SolicitudActualizacion solicitud = cola.peek();

                verificar(
                    solicitud.getId() == 1,
                    "La primera solicitud debería ser la de ID 1."
                );

                verificar(
                    solicitud.getEstado().equals(SolicitudActualizacion.PENDIENTE),
                    "La solicitud debería estar pendiente."
                );
            }
        );

        probar(
            "Peek no modifica el total de la cola",
            null,
            () -> {
                verificar(
                    cola.getTotal() == 2,
                    "Peek no debería modificar el total de la cola."
                );
            }
        );

        probar(
            "Dequeue devuelve la primera solicitud FIFO",
            null,
            () -> {
                SolicitudActualizacion solicitud = cola.dequeue();

                verificar(
                    solicitud.getId() == 1,
                    "El dequeue debería devolver la solicitud de ID 1."
                );
            }
        );

        probar(
            "Verificar total después de primer dequeue",
            null,
            () -> {
                verificar(
                    cola.getTotal() == 1,
                    "La cola debería tener 1 solicitud después del primer dequeue."
                );
            }
        );

        probar(
            "Dequeue devuelve la segunda solicitud",
            null,
            () -> {
                SolicitudActualizacion solicitud = cola.dequeue();

                verificar(
                    solicitud.getId() == 2,
                    "El dequeue debería devolver la solicitud de ID 2."
                );
            }
        );

        probar(
            "Cola queda vacía después de dequeues",
            null,
            () -> {
                verificar(
                    cola.estaVacia(),
                    "La cola debería quedar vacía después de dos dequeues."
                );
            }
        );

        probar(
            "Listar cola vacía",
            null,
            () -> {
                verificar(
                    cola.listar().equals("Cola de solicitudes vacía."),
                    "El listado debería indicar que la cola está vacía."
                );
            }
        );

        probar(
            "Encolar después de vaciar la cola",
            null,
            () -> cola.enqueue(
                new SolicitudActualizacion(
                    3,
                    "Informática",
                    "Bruno",
                    null,
                    RegistroHistorial.CATEGORIA_DOCENTE,
                    "Titular"
                )
            )
        );

        probar(
            "Verificar listado con una solicitud",
            null,
            () -> {
                String listado = cola.listar();

                verificar(
                    listado.contains("ID: 3"),
                    "El listado debería contener la solicitud de ID 3."
                );
            }
        );

        probar(
            "Verificar orden FIFO en listado",
            null,
            () -> {
                ColaSolicitudes cola2 = new ColaSolicitudes();

                cola2.enqueue(
                    new SolicitudActualizacion(
                        1,
                        "Informática",
                        "Ana",
                        null,
                        RegistroHistorial.EVALUACION,
                        "85"
                    )
                );

                cola2.enqueue(
                    new SolicitudActualizacion(
                        2,
                        "Informática",
                        "Ana",
                        null,
                        RegistroHistorial.EVALUACION,
                        "90"
                    )
                );

                cola2.enqueue(
                    new SolicitudActualizacion(
                        3,
                        "Informática",
                        "Ana",
                        null,
                        RegistroHistorial.EVALUACION,
                        "95"
                    )
                );

                String listado = cola2.listar();

                int id1 = listado.indexOf("ID: 1");
                int id2 = listado.indexOf("ID: 2");
                int id3 = listado.indexOf("ID: 3");

                verificar(
                    id1 >= 0 && id2 >= 0 && id3 >= 0,
                    "El listado debería contener las tres solicitudes."
                );

                verificar(
                    id1 < id2 && id2 < id3,
                    "El listado debería mostrar las solicitudes en orden FIFO."
                );
            }
        );

        probar(
            "Solicitud inicia pendiente",
            null,
            () -> {
                SolicitudActualizacion solicitud = new SolicitudActualizacion(
                    4,
                    "Informática",
                    "Carla",
                    null,
                    RegistroHistorial.GRADO_CIENTIFICO,
                    "Doctora"
                );

                verificar(
                    solicitud.getEstado().equals(SolicitudActualizacion.PENDIENTE),
                    "La solicitud debería iniciar pendiente."
                );
            }
        );

        probar(
            "Procesar solicitud cambia estado",
            null,
            () -> {
                SolicitudActualizacion solicitud = new SolicitudActualizacion(
                    5,
                    "Informática",
                    "Carla",
                    null,
                    RegistroHistorial.GRADO_CIENTIFICO,
                    "Doctora"
                );

                solicitud.procesar();

                verificar(
                    solicitud.getEstado().equals(SolicitudActualizacion.PROCESADA),
                    "La solicitud debería quedar procesada."
                );
            }
        );

        probar(
            "Rechazar solicitud cambia estado",
            null,
            () -> {
                SolicitudActualizacion solicitud = new SolicitudActualizacion(
                    6,
                    "Informática",
                    "Carla",
                    null,
                    RegistroHistorial.GRADO_CIENTIFICO,
                    "Doctora"
                );

                solicitud.rechazar();

                verificar(
                    solicitud.getEstado().equals(SolicitudActualizacion.RECHAZADA),
                    "La solicitud debería quedar rechazada."
                );
            }
        );

        probar(
            "Procesar solicitud ya procesada lanza excepción",
            DatoInvalidoException.class,
            () -> {
                SolicitudActualizacion solicitud = new SolicitudActualizacion(
                    7,
                    "Informática",
                    "Carla",
                    null,
                    RegistroHistorial.GRADO_CIENTIFICO,
                    "Doctora"
                );

                solicitud.procesar();
                solicitud.procesar();
            }
        );
    }

        private static void pruebasSistema() {

        System.out.println();
        System.out.println("====================================");
        System.out.println("PRUEBAS DEL SISTEMA INTEGRADO");
        System.out.println("====================================");

        SistemaGestionAcademica sistema = new SistemaGestionAcademica();

        probar(
            "Sistema inicia vacío",
            null,
            () -> {
                verificar(
                    sistema.getTotalDepartamentos() == 0,
                    "El sistema debería iniciar sin departamentos."
                );

                verificar(
                    sistema.contarProfesoresDestacados() == 0,
                    "El sistema debería iniciar sin profesores destacados."
                );
            }
        );

        probar(
            "Crear departamento válido",
            null,
            () -> sistema.crearDepartamento("Informática")
        );

        probar(
            "Crear departamento duplicado",
            ElementoDuplicadoException.class,
            () -> sistema.crearDepartamento("Informática")
        );

        probar(
            "Crear profesor válido",
            null,
            () -> sistema.crearProfesor(
                "Informática",
                "Ana",
                "Asistente",
                "Licenciada",
                30,
                80
            )
        );

        probar(
            "Crear profesor duplicado",
            ElementoDuplicadoException.class,
            () -> sistema.crearProfesor(
                "Informática",
                "Ana",
                "Titular",
                "Doctora",
                40,
                90
            )
        );

        probar(
            "Crear asignatura válida",
            null,
            () -> sistema.crearAsignatura(
                "Informática",
                "Ana",
                "Matemática",
                100
            )
        );

        probar(
            "Crear asignatura duplicada",
            ElementoDuplicadoException.class,
            () -> sistema.crearAsignatura(
                "Informática",
                "Ana",
                "Matemática",
                50
            )
        );

        probar(
            "Verificar conteo inicial de destacados",
            null,
            () -> {
                verificar(
                    sistema.contarProfesoresDestacados() == 0,
                    "Ana con 100 horas no debería ser destacada."
                );
            }
        );

        probar(
            "Actualizar horas de asignatura",
            null,
            () -> sistema.actualizarHorasAsignatura(
                "Informática",
                "Ana",
                "Matemática",
                121
            )
        );

        probar(
            "Verificar destacado tras actualizar horas",
            null,
            () -> {
                verificar(
                    sistema.contarProfesoresDestacados() == 1,
                    "Ana con 121 horas debería ser destacada."
                );
            }
        );

        probar(
            "Verificar historial tras actualizar horas",
            null,
            () -> {
                verificar(
                    sistema.getTotalHistorial() == 1,
                    "Debería existir un cambio en la pila de historial."
                );
            }
        );

        probar(
            "Deshacer cambio de horas",
            null,
            () -> sistema.deshacerUltimoCambio()
        );

        probar(
            "Verificar horas restauradas",
            null,
            () -> {
                verificar(
                    sistema.buscarAsignatura(
                        "Informática",
                        "Ana",
                        "Matemática"
                    ).getHoras() == 100,
                    "Las horas deberían volver a 100 después de deshacer."
                );
            }
        );

        probar(
            "Verificar historial vacío tras deshacer",
            null,
            () -> {
                verificar(
                    sistema.getTotalHistorial() == 0,
                    "La pila debería quedar vacía después de deshacer."
                );
            }
        );

        probar(
            "Actualizar evaluación directa",
            null,
            () -> sistema.actualizarProfesor(
                "Informática",
                "Ana",
                RegistroHistorial.EVALUACION,
                "90"
            )
        );

        probar(
            "Verificar evaluación actualizada",
            null,
            () -> {
                verificar(
                    sistema.buscarProfesor("Informática", "Ana")
                        .getEvaluacion() == 90,
                    "La evaluación debería ser 90."
                );
            }
        );

        probar(
            "Deshacer cambio de evaluación",
            null,
            () -> sistema.deshacerUltimoCambio()
        );

        probar(
            "Verificar evaluación restaurada",
            null,
            () -> {
                verificar(
                    sistema.buscarProfesor("Informática", "Ana")
                        .getEvaluacion() == 80,
                    "La evaluación debería volver a 80 después de deshacer."
                );
            }
        );

        probar(
            "Registrar solicitud válida de evaluación",
            null,
            () -> sistema.registrarSolicitud(
                "Informática",
                "Ana",
                null,
                RegistroHistorial.EVALUACION,
                "95"
            )
        );

        probar(
            "Procesar solicitud de evaluación",
            null,
            () -> {
                SolicitudActualizacion solicitud =
                    sistema.procesarSiguienteSolicitud();

                verificar(
                    solicitud.getEstado()
                        .equals(SolicitudActualizacion.PROCESADA),
                    "La solicitud debería quedar procesada."
                );
            }
        );

        probar(
            "Verificar evaluación tras solicitud",
            null,
            () -> {
                verificar(
                    sistema.buscarProfesor("Informática", "Ana")
                        .getEvaluacion() == 95,
                    "La evaluación debería ser 95 después de procesar la solicitud."
                );
            }
        );

        probar(
            "Registrar solicitud válida de horas",
            null,
            () -> sistema.registrarSolicitud(
                "Informática",
                "Ana",
                "Matemática",
                RegistroHistorial.HORAS_ASIGNATURA,
                "130"
            )
        );

        probar(
            "Procesar solicitud de horas",
            null,
            () -> {
                SolicitudActualizacion solicitud =
                    sistema.procesarSiguienteSolicitud();

                verificar(
                    solicitud.getEstado()
                        .equals(SolicitudActualizacion.PROCESADA),
                    "La solicitud de horas debería quedar procesada."
                );
            }
        );

        probar(
            "Verificar destacado tras solicitud de horas",
            null,
            () -> {
                verificar(
                    sistema.contarProfesoresDestacados() == 1,
                    "Ana con 130 horas debería ser destacada."
                );
            }
        );

        probar(
            "Deshacer solicitud de horas",
            null,
            () -> sistema.deshacerUltimoCambio()
        );

        probar(
            "Verificar horas restauradas tras solicitud",
            null,
            () -> {
                verificar(
                    sistema.buscarAsignatura(
                        "Informática",
                        "Ana",
                        "Matemática"
                    ).getHoras() == 100,
                    "Las horas deberían volver a 100 después de deshacer la solicitud."
                );
            }
        );

        probar(
            "Deshacer solicitud de evaluación",
            null,
            () -> sistema.deshacerUltimoCambio()
        );

        probar(
            "Verificar evaluación restaurada tras solicitud",
            null,
            () -> {
                verificar(
                    sistema.buscarProfesor("Informática", "Ana")
                        .getEvaluacion() == 80,
                    "La evaluación debería volver a 80 después de deshacer la solicitud."
                );
            }
        );

        probar(
            "Procesar cola vacía",
            EstructuraVaciaException.class,
            () -> sistema.procesarSiguienteSolicitud()
        );

        probar(
            "Registrar solicitud con profesor inexistente",
            null,
            () -> sistema.registrarSolicitud(
                "Informática",
                "NoExiste",
                null,
                RegistroHistorial.EVALUACION,
                "90"
            )
        );

        probar(
            "Procesar solicitud con profesor inexistente la rechaza",
            null,
            () -> {
                SolicitudActualizacion solicitud =
                    sistema.procesarSiguienteSolicitud();

                verificar(
                    solicitud.getEstado()
                        .equals(SolicitudActualizacion.RECHAZADA),
                    "La solicitud debería quedar rechazada."
                );
            }
        );

        probar(
            "Verificar solicitudes vacías tras rechazo",
            null,
            () -> {
                verificar(
                    sistema.getTotalSolicitudes() == 0,
                    "La cola debería quedar vacía después de procesar la solicitud."
                );
            }
        );

        probar(
            "Verificar historial sin cambios tras rechazo",
            null,
            () -> {
                verificar(
                    sistema.getTotalHistorial() == 0,
                    "Una solicitud rechazada no debería generar historial."
                );
            }
        );

        probar(
            "Actualizar profesor con tipo de asignatura lanza excepción",
            DatoInvalidoException.class,
            () -> sistema.actualizarProfesor(
                "Informática",
                "Ana",
                RegistroHistorial.HORAS_ASIGNATURA,
                "100"
            )
        );

        probar(
            "Actualizar horas de asignatura inexistente",
            ElementoNoEncontradoException.class,
            () -> sistema.actualizarHorasAsignatura(
                "Informática",
                "Ana",
                "Física",
                100
            )
        );

        probar(
            "Crear segundo departamento",
            null,
            () -> sistema.crearDepartamento("Matemática")
        );

        probar(
            "Crear profesor en segundo departamento",
            null,
            () -> sistema.crearProfesor(
                "Matemática",
                "Bruno",
                "Titular",
                "Doctor",
                45,
                95
            )
        );

        probar(
            "Crear asignatura destacada en segundo departamento",
            null,
            () -> sistema.crearAsignatura(
                "Matemática",
                "Bruno",
                "Cálculo",
                121
            )
        );

        probar(
            "Verificar destacados con segundo departamento",
            null,
            () -> {
                verificar(
                    sistema.contarProfesoresDestacados() == 1,
                    "Bruno debería ser destacado."
                );
            }
        );

        probar(
            "Eliminar profesor destacado",
            null,
            () -> sistema.eliminarProfesor("Matemática", "Bruno")
        );

        probar(
            "Verificar destacados tras eliminar profesor",
            null,
            () -> {
                verificar(
                    sistema.contarProfesoresDestacados() == 0,
                    "No deberían quedar profesores destacados."
                );
            }
        );

        probar(
            "Eliminar departamento",
            null,
            () -> sistema.eliminarDepartamento("Matemática")
        );

        probar(
            "Verificar departamentos tras eliminar",
            null,
            () -> {
                verificar(
                    sistema.getTotalDepartamentos() == 1,
                    "Debería quedar solo Informática."
                );
            }
        );
    }
    
}
