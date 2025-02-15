# Chat TCP Elías Martín
Chat TCP desarrollado para las asignaturas de 'Programación de Servicios y Procesos' y 'Desarrollo de Interfaces'.

El servidor se encuentra corriendo en **chatpsp-elias.ddns.net**:10101.

## IMPORTANTE
Si el cliente no puede conectarse se deberá ejecutar el servidor de forma local antes que los clientes.

## Cómo Ejecutar
### Opciones de ejecución del servidor en local:
- Docker
> docker run --rm -it --name chatServer -p 10101:10101 eliazzzdev/chat_server:latest
 - IDE
> Ejecutar la clase com.dam.elias.chat.server.ServerMain

### Ejecución del cliente:
  Ejecutar desde el IDE la clase com.dam.elias.chat.App
  - Parámetros de lanzamiento para ejecutar con conexión al servidor online:
>    -ip chatpsp-elias.ddns.net -port 10101
  - Parámetros de lanzamiento para ejecutar en local:
>    -ip localhost -port 10101

## Funcionalidades
1. Inicio de sesión (no admite usuarios repetidos)
2. Sala común para todos los usuarios conectados \[ALL]
3. Buscador de usuarios online
4. Chats privados
5. Chats en grupo (no se puede salir ni añadir gente tras crearlo)
6. Buscador de chats 
7. Número de mensajes no leídos

### Errores conocidos
- La burbuja de mensajes no se adapta al texto del mensaje, cortando los mensajes a partir de cierta longitud.
- No se informa de la desconexión del otro usuario en la conversación

#### Otros
- Hay fragmentos de código de funcionalidades no implementadas.
- Hay excepciones no manejadas de forma debida en la aplicación gráfica del cliente.
