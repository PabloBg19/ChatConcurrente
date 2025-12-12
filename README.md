# 💬 Chat Concurrente en Java (Sockets + Threads)

![Java](https://img.shields.io/badge/Java-Sockets%20%7C%20Threads-blue)
![Estado](https://img.shields.io/badge/Estado-Completado-success)
![PSP](https://img.shields.io/badge/Asignatura-PSP-orange)

---

## 📌 Descripción General

Este repositorio contiene el desarrollo completo de la práctica **“Servidor de Chat Concurrente”** de la asignatura **Programación de Servicios y Procesos (PSP)**.

El objetivo principal del proyecto es transformar un servidor **bloqueante y de comunicación única** en un **servidor de chat multihilo**, capaz de atender a **múltiples clientes simultáneamente**, manteniendo conversaciones fluidas, robustas y profesionales.

El proyecto ha sido desarrollado siguiendo una metodología incremental por fases, añadiendo complejidad y calidad progresivamente.

---

## 🎯 Objetivos del Proyecto

- ✅ Implementar comunicación cliente-servidor usando **Java Sockets**
- ✅ Permitir conversaciones **indefinidas** mediante bucles
- ✅ Implementar un **servidor concurrente** usando **Threads**
- ✅ Gestionar correctamente el cierre de conexiones
- ✅ Manejar desconexiones inesperadas sin que el servidor falle
- ✅ Aplicar buenas prácticas de programación y diseño

---

## 🧠 Metodología de Trabajo

El desarrollo se ha realizado siguiendo la técnica de **Pair Programming**:

- 👨‍✈️ **Piloto**: escribe el código
- 🧭 **Copiloto**: revisa, detecta errores y guía la lógica
- 🔁 Se alternan los roles en cada fase

Esto garantiza mayor calidad, revisión continua y aprendizaje colaborativo.

---

## 🏗️ Estructura del Proyecto

```
ChatConcurrente/
│
├── src/
│   └── Fase3/
│       ├── Server.java          # Servidor principal
│       ├── GestorCliente.java   # Hilo que gestiona cada cliente
│       └── Client.java          # Cliente del chat
│
├── README.md
└── memoria.pdf (entrega académica)
```

---

## 🔹 Fase 1: Análisis del Bloqueo

### 📖 Objetivo
Comprender por qué un servidor sin hilos **no puede atender a más de un cliente**.

### 🧪 Experimento Realizado
Se añadió un `Thread.sleep(15000)` tras `server.accept()`.

### ❓ Pregunta del Informe
**¿Qué ocurre con el Cliente 2 y por qué?**

### ✅ Respuesta
El segundo cliente queda bloqueado porque:
- El **hilo principal** del servidor está dormido
- No puede volver a ejecutar `accept()`
- No existen hilos independientes para cada cliente

👉 **Conclusión**: sin hilos, el servidor es egoísta y bloqueante.

---

## 🔹 Fase 2: Conversación Fluida (1 a 1)

### 🎯 Objetivo
Permitir que cliente y servidor hablen indefinidamente.

### 🛠️ Implementación
- Bucle `while (!salir)` en cliente y servidor
- Protocolo de finalización con la palabra **FIN**
- Uso de `Scanner` para entrada por teclado
- Gestión correcta de recursos (`close()` / `try-with-resources`)

### 🔐 Protocolo FIN
- La conversación **solo termina** cuando el cliente escribe `FIN`
- El cliente **no espera respuesta** tras enviar FIN

---

## 🔹 Fase 3: Servidor Multihilo (Concurrente)

### 🧠 Objetivo Principal
Permitir que **varios clientes se conecten y hablen al mismo tiempo**.

### 🧩 Solución Implementada

#### Clase `GestorCliente`
- Implementa `Runnable`
- Contiene su propio `Socket`
- Gestiona toda la conversación con un cliente
- Se ejecuta en un hilo independiente

#### Servidor (`Server.java`)
El `main` queda reducido a:

1. Esperar conexión (`accept()`)
2. Crear un `GestorCliente`
3. Lanzar un nuevo hilo
4. Volver a esperar

👉 El servidor **nunca se bloquea**

### 📸 Capturas de Pantalla (obligatorias en la memoria)

> 📷 **Aquí insertar capturas**
>
> - Consola del servidor
> - Cliente 1 enviando mensajes
> - Cliente 2 conectado simultáneamente

---

## 🔹 Fase 4: Mejoras Profesionales (Bonus)

### 🆔 Identificación por IP
Cuando un cliente se conecta, el servidor muestra:

```
Cliente conectado desde: 127.0.0.1
```

Implementado usando:
```java
socket.getInetAddress().getHostAddress()
```

### ⚠️ Desconexión Abrupta
Si el cliente cierra la ventana sin escribir `FIN`:

- Se captura `EOFException` / `SocketException`
- El servidor **NO se cae**
- Se muestra un mensaje elegante:

```
El cliente se ha desconectado inesperadamente
```

---

## 🧾 Memoria del Proyecto

La memoria entregada en PDF incluye:

- 👥 Nombres de los integrantes
- 🧠 Análisis de la Fase 1 (bloqueo)
- 📸 Capturas demostrando concurrencia real
- 📊 Explicación técnica del funcionamiento

---

## 📊 Evaluación (Rúbrica)

| Criterio | Estado |
|--------|--------|
| Comunicación en bucle | ✅ |
| Concurrencia real | ✅ |
| Uso correcto de hilos | ✅ |
| Gestión de recursos | ✅ |
| Manejo de errores | ✅ |
| Análisis teórico | ✅ |

⚠️ **Nota importante**:  
El proyecto incluye comentarios explicativos y sigue buenas prácticas para evitar penalizaciones.

---

## 🚀 Cómo Ejecutar el Proyecto

1. Ejecutar `Server.java`
2. Ejecutar uno o varios `Client.java`
3. Introducir nombre de usuario
4. Enviar mensajes
5. Escribir `FIN` para salir

---

## 🧑‍💻 Tecnologías Utilizadas

- ☕ Java
- 🔌 Sockets TCP
- 🧵 Threads (`Runnable`)
- 📥 DataInputStream / DataOutputStream
- 🖥️ Consola

---

## 🏁 Conclusión

Este proyecto demuestra de forma práctica:

- La necesidad de concurrencia en servidores reales
- El uso correcto de hilos en Java
- El diseño de protocolos de comunicación
- La importancia de la robustez ante errores

📌 **Resultado**: Un servidor de chat concurrente funcional, robusto y profesional.

---

✍️ *Proyecto académico – PSP*
