# Gestión Académica y Carga Docente

Sistema de gestión académica desarrollado en Java como proyecto del Equipo 1 para la asignatura **Estructuras de Datos**.

El proyecto permite administrar la carga docente de diferentes departamentos universitarios mediante estructuras de datos enlazadas implementadas manualmente. Incluye operaciones de gestión de departamentos, profesores y asignaturas, un sistema de historial de modificaciones y una cola para procesar solicitudes de actualización.

## 📌 Objetivo del proyecto

El objetivo es implementar un sistema que permita organizar y administrar información académica, aplicando conceptos fundamentales de estructuras de datos, recursividad y programación orientada a objetos.

La solución utiliza estructuras enlazadas propias, sin recurrir a `ArrayList`, `LinkedList`, `Stack` o `Queue` para implementar las estructuras centrales del sistema.

## ⚙️ Funcionalidades principales

### 1. Gestión de departamentos

Permite:
- Crear nuevos departamentos.
- Buscar departamentos por nombre.
- Listar los departamentos registrados.
- Eliminar departamentos.
- Consultar la cantidad total de departamentos.
- Contar los profesores destacados de todos los departamentos.

### 2. Gestión de profesores

Cada profesor pertenece a un departamento y almacena los siguientes datos:

| Atributo | Descripción |
|---|---|
| Nombre | Identificación del profesor. |
| Categoría docente | Categoría académica del profesor. |
| Grado científico | Grado científico alcanzado. |
| Edad | Edad del profesor. |
| Evaluación | Evaluación comprendida entre 0 y 100. |
| Asignaturas | Asignaturas impartidas por el profesor. |

Operaciones disponibles:
- Registrar profesores.
- Buscar profesores dentro de un departamento.
- Listar profesores.
- Eliminar profesores.
- Actualizar categoría docente, grado científico, edad y evaluación.
- Consultar las horas docentes acumuladas.
- Identificar profesores destacados.

### 3. Gestión de asignaturas

Cada profesor tiene asociada una lista de asignaturas.

Cada asignatura almacena:
- Nombre de la asignatura.
- Cantidad de horas docentes.

El sistema permite:
- Registrar asignaturas.
- Buscar asignaturas.
- Listar las asignaturas de un profesor.
- Eliminar asignaturas.
- Actualizar las horas asignadas.
- Calcular el total de horas docentes de un profesor.

### 4. Historial de modificaciones

El sistema incorpora un mecanismo para registrar y deshacer las modificaciones realizadas sobre los datos docentes.

Cada registro almacena:
- Número de operación.
- Descripción de la modificación.
- Departamento afectado.
- Profesor afectado.
- Asignatura afectada, cuando corresponda.
- Tipo de modificación.
- Valor anterior.
- Valor nuevo.

Las modificaciones registradas incluyen:
- Categoría docente.
- Grado científico.
- Edad.
- Evaluación.
- Horas de una asignatura.

Se puede consultar el último cambio y deshacerlo, restaurando el valor anterior.

### 5. Procesamiento de solicitudes de actualización

Se implementa un sistema de solicitudes mediante una cola FIFO (*First In, First Out*).

Cada solicitud contiene:
- Identificador.
- Departamento.
- Profesor.
- Asignatura, cuando corresponda.
- Tipo de actualización.
- Nuevo valor.
- Estado de procesamiento.

Las solicitudes pueden encontrarse en tres estados:

| Estado | Descripción |
|---|---|
| `PENDIENTE` | La solicitud todavía no ha sido atendida. |
| `PROCESADA` | La actualización se aplicó correctamente. |
| `RECHAZADA` | No fue posible aplicar la actualización. |

El sistema procesa las solicitudes en el orden en que fueron recibidas. Las actualizaciones aplicadas correctamente también quedan registradas en el historial, permitiendo deshacerlas posteriormente.

## 🧩 Estructuras de datos implementadas

Uno de los aspectos fundamentales del proyecto es la implementación manual de las estructuras de datos utilizadas para administrar la información.

### 1. Multilista de departamentos, profesores y asignaturas

La información se organiza jerárquicamente mediante listas simplemente enlazadas.

```text
ListaDepartamentos
       |
       v
  Departamento
       |
       v
  ListaProfesores
       |
       v
    Profesor
       |
       v
  ListaAsignaturas
       |
       v
   Asignatura
```

**Funcionamiento:**

- `ListaDepartamentos`: administra los departamentos registrados.
- `ListaProfesores`: administra los profesores pertenecientes a un departamento.
- `ListaAsignaturas`: administra las asignaturas impartidas por cada profesor.

Cada lista utiliza nodos enlazados mediante referencias al siguiente elemento.

**Reglas de organización:**

- Los departamentos se insertan al final de la lista, respetando el orden de creación.
- Los profesores se insertan alfabéticamente.
- Las asignaturas se insertan alfabéticamente.
- No se permiten elementos duplicados dentro de un mismo nivel.
- Las búsquedas y operaciones de recorrido se implementan mediante métodos recursivos.

### 2. Pila de historial (LIFO)

La clase `PilaHistorial` implementa una pila mediante nodos enlazados.

Sigue el principio LIFO (*Last In, First Out*): el último registro añadido es el primero que se recupera.

Operaciones principales:

| Operación | Descripción |
|---|---|
| `push()` | Inserta un nuevo registro en la cima. |
| `pop()` | Extrae el registro situado en la cima. |
| `peek()` | Consulta el último registro sin eliminarlo. |
| `listar()` | Muestra los registros del historial. |

Esta estructura permite implementar el mecanismo para deshacer la última modificación.

### 3. Cola de solicitudes (FIFO)

La clase `ColaSolicitudes` implementa una cola mediante nodos enlazados.

Sigue el principio FIFO (*First In, First Out*): la primera solicitud registrada es la primera que se procesa.

La estructura mantiene referencias al frente y al fondo de la cola.

Operaciones principales:

| Operación | Descripción |
|---|---|
| `enqueue()` | Añade una solicitud al final. |
| `dequeue()` | Extrae la solicitud situada al frente. |
| `peek()` | Consulta la siguiente solicitud sin extraerla. |
| `listar()` | Muestra las solicitudes pendientes. |

Esta estructura garantiza el procesamiento ordenado de las solicitudes de actualización.

## 🔄 Conteo recursivo de profesores destacados

El sistema incluye un algoritmo recursivo para identificar y contabilizar profesores destacados.

**Criterio de clasificación:**

Un profesor se considera destacado cuando la suma de las horas de todas sus asignaturas es estrictamente mayor que 120.

```java
profesor.getTotalHoras() > 120
```

Por tanto:
- 100 horas: no destacado.
- 120 horas: no destacado.
- 121 horas: destacado.
- 150 horas: destacado.

**Funcionamiento del algoritmo:**

1. La lista de departamentos se recorre recursivamente.
2. Para cada departamento se recorre su lista de profesores.
3. Cada profesor calcula recursivamente la suma de las horas de sus asignaturas.
4. Se contabilizan los profesores cuya carga docente supera las 120 horas.
5. Los resultados parciales se acumulan hasta obtener el total general.

Este algoritmo permite demostrar la aplicación de recursividad sobre estructuras enlazadas de diferentes niveles.

## 🛡️ Validaciones y manejo de excepciones

El sistema incorpora excepciones personalizadas para controlar operaciones inválidas y mantener la consistencia de los datos.

Entre las situaciones controladas se encuentran:

- Intentar registrar elementos nulos.
- Introducir nombres vacíos.
- Registrar elementos duplicados.
- Buscar elementos inexistentes.
- Introducir horas de asignaturas menores o iguales a cero.
- Asignar evaluaciones fuera del intervalo de 0 a 100.
- Registrar edades negativas.
- Intentar consultar o extraer elementos de estructuras vacías.
- Intentar procesar solicitudes que no pueden aplicarse.

Las excepciones personalizadas permiten diferenciar los errores de validación, duplicidad, búsqueda y acceso a estructuras vacías.

## 📁 Organización del proyecto

```text
Parcial-2/
│
├── src/
│   └── com/
│       └── equipo1/
│           └── gestionacademica/
│               │
│               ├── Main.java
│               ├── SistemaGestionAcademica.java
│               │
│               ├── estructuras/
│               │   ├── ListaDepartamentos.java
│               │   ├── ListaProfesores.java
│               │   ├── ListaAsignaturas.java
│               │   ├── PilaHistorial.java
│               │   ├── ColaSolicitudes.java
│               │   └── Nodos auxiliares
│               │
│               ├── modelo/
│               │   ├── Departamento.java
│               │   ├── Profesor.java
│               │   ├── Asignatura.java
│               │   ├── RegistroHistorial.java
│               │   └── SolicitudActualizacion.java
│               │
│               └── excepciones/
│                   └── Excepciones personalizadas
│
├── documentación/
│   └── Presentación del proyecto
│
└── README.md
```

### Responsabilidad de las clases principales

| Clase | Responsabilidad |
|---|---|
| `Main` | Punto de entrada y ejecución de las pruebas. |
| `SistemaGestionAcademica` | Coordina las operaciones generales del sistema. |
| `Departamento` | Representa un departamento académico. |
| `Profesor` | Representa un profesor y administra sus asignaturas. |
| `Asignatura` | Representa una asignatura y sus horas docentes. |
| `RegistroHistorial` | Almacena información de las modificaciones. |
| `SolicitudActualizacion` | Representa una solicitud de actualización. |

## 🧪 Pruebas del sistema

El proyecto incluye un conjunto de pruebas implementadas en `Main.java`, organizadas en diferentes grupos:

- Pruebas de asignaturas.
- Pruebas de profesores.
- Pruebas de departamentos.
- Pruebas de la pila de historial.
- Pruebas de la cola de solicitudes.
- Pruebas integradas del sistema.

Se comprueban tanto operaciones correctas como situaciones excepcionales, incluyendo:
- Inserciones y eliminaciones.
- Búsquedas existentes e inexistentes.
- Elementos duplicados.
- Datos inválidos.
- Estructuras vacías.
- Actualizaciones y recuperación de valores anteriores.
- Procesamiento y rechazo de solicitudes.
- Conteo de profesores destacados.

Al finalizar, el programa muestra un resumen con la cantidad de pruebas exitosas y fallidas.

## 💻 Requisitos

Para compilar y ejecutar el proyecto se necesita:

- JDK 8 o superior.
- Terminal o consola de comandos.
- Un editor o entorno de desarrollo Java (IntelliJ IDEA, Eclipse, NetBeans o similar).

No se requieren bibliotecas externas.

## 🚀 Compilación y ejecución

Clonar el repositorio:

```bash
git clone https://github.com/RelaxUniver/Parcial-2.git
```

Entrar en el directorio:

```bash
cd Parcial-2
```

### Linux / macOS (Bash)

```bash
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name '*.java')
java -cp out com.equipo1.gestionacademica.Main
```

### Windows (PowerShell)

```powershell
New-Item -ItemType Directory -Force out | Out-Null

$sources = Get-ChildItem -Recurse src -Filter *.java | ForEach-Object FullName

javac -encoding UTF-8 -d out $sources

java -cp out com.equipo1.gestionacademica.Main
```

Al ejecutar el programa se realizarán las pruebas implementadas y se mostrará el resumen de resultados.

## 📊 Presentación

[Consultar la presentación del proyecto](documentación/Presentacíon.pptx)

## 👥 Integrantes del equipo

**Equipo 1 — Estructuras de Datos**

- Yaisel Pinos Durán
- Merlyn Sosa Borges
- Geonel Sánchez
- Jorge Luis Reyes Pérez
- Ray Sanjudo Mesa

## 🤖 Declaración sobre el uso de IA

La IA se utilizó solo como apoyo para escribir más rápido código, pruebas y documentación. No se utilizó para pensar la solución ni para tomar las decisiones principales. El diseño, el análisis y la comprensión del proyecto fueron realizados por el equipo.
