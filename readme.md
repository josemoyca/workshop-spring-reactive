Simular un error en el guardado de la cuenta, crear un
mensaje de error que tenga la información de la transacción para que
elimine la transacción

Crear un servicio dentro de este mismo proyecto que funcione como cloud watch
en donde mandemos todas las acciones que se realizan con un mensaje
y que deje la evidencia de la acción realizada
tanto de error como de exito, ademas de dejar la trazabilidad de toda la
ejecución, cuando una peticion entra, cuando se ejecuta un llamado a la base de datos
y la notificación de finalización de la ejecución.

Crear objeto de resiliencia que contenga la cuenta y la
transacción para enviar a una nueva cola de Rabbit con un
nuevo routing key.

Crear un handler que consuma esa cola y borrar los registros ya guardados
                                     