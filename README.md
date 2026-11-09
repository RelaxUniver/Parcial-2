# Gestión Académica y Carga Docente

Proyecto del Equipo 1 para la asignatura Estructuras de Datos. El sistema permite administrar departamentos, profesores y asignaturas, registrar cambios en los datos docentes y procesar solicitudes de actualización.

## Integrantes

- Yaisel Pinos Durán
- Merlyn Sosa Borges
- Geonel Sánchez
- Jorge Luis Reyes Pérez
- Ray Sanjudo Mesa

## Problema y solución

La aplicación organiza la carga docente de los profesores de distintos departamentos. Permite crear, buscar, listar y eliminar departamentos, profesores y asignaturas; consultar horas docentes; actualizar datos; deshacer la última modificación y tramitar solicitudes de actualización.

Se implementó en Java con estructuras enlazadas propias, sin utilizar `ArrayList`, `LinkedList`, `Stack` ni `Queue` para las estructuras centrales.

## Estructuras de datos

### Multilista de departamentos, profesores y asignaturas

La información se organiza jerárquicamente:

```text
ListaDepartamentos
└── Departamento
    └── ListaProfesores
        └── Profesor
            └── ListaAsignaturas
                └── Asignatura
```

Cada departamento contiene profesores y cada profesor contiene asignaturas. Los nombres identifican de forma única los elementos dentro de su nivel. Los departamentos se agregan en orden de creación; profesores y asignaturas se insertan alfabéticamente.

### Pila de historial

`PilaHistorial` sigue el principio LIFO: el último cambio registrado es el primero que se consulta o deshace. Cada registro conserva el elemento afectado, el tipo de cambio y los valores anterior y nuevo. Se registran cambios de categoría docente, grado científico, edad, evaluación y horas de asignatura.

### Cola de solicitudes

`ColaSolicitudes` sigue el principio FIFO: las solicitudes se procesan en el orden en que se registraron. Una solicitud puede quedar `PENDIENTE`, `PROCESADA` o `RECHAZADA`. Los cambios aplicados correctamente también se agregan al historial.

## Conteo recursivo de profesores destacados

Un profesor se considera destacado cuando la suma de horas de sus asignaturas es **mayor que 120**. El conteo recorre recursivamente la lista de departamentos y las listas de profesores; cada profesor calcula su total recorriendo recursivamente sus asignaturas. Con exactamente 120 horas, no se considera destacado.

## Pruebas

`Main.java` contiene un conjunto de pruebas manuales que comprueba operaciones válidas, entradas inválidas, duplicados, búsquedas de elementos inexistentes, estructuras vacías, solicitudes, historial y el límite de horas para profesores destacados. Al ejecutarse, informa el total de pruebas exitosas y fallidas.

**Estado actual:** `src/com/equipo1/gestionacademica/estructuras/NodoProfesor.java` está vacío en esta versión, por lo que el proyecto no compila todavía y no es posible confirmar el resultado de las pruebas. Debe completarse esa clase antes de ejecutar los comandos siguientes.

## Compilar y ejecutar

Desde la raíz del proyecto, en Bash:

```bash
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name '*.java')
java -cp out com.equipo1.gestionacademica.Main
```

En PowerShell:

```powershell
New-Item -ItemType Directory -Force out | Out-Null
$sources = Get-ChildItem -Recurse src -Filter *.java | ForEach-Object FullName
javac -encoding UTF-8 -d out $sources
java -cp out com.equipo1.gestionacademica.Main
```

## Presentación

[Abrir la presentación del proyecto](documentación/Presentacíon.pptx)

## Declaración sobre el uso de IA

Se utilizó inteligencia artificial como apoyo para revisar el repositorio, integrar archivos Java proporcionados, organizar y redactar esta documentación. El equipo debe revisar el resultado, validar el funcionamiento del programa y poder explicar sus decisiones y su código.
