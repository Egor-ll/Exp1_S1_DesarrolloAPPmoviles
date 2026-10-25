# Pizzeria Kotlin

Aplicación de consola desarrollada en Kotlin para simular la gestión
básica de pedidos de una pizzería. Este proyecto corresponde a la
actividad formativa **"Mi primera app en Kotlin"** de la Semana 1 del
módulo Desarrollo de Aplicaciones Móviles (PMY2201).

## Objetivo

Aplicar los fundamentos de Kotlin mediante el registro de clientes, la
selección de productos, el cálculo de precios y descuentos, la
validación de stock y la generación de un resumen del pedido.

## Funcionalidades

-   Registro de nombre, dirección, teléfono e identificador del pedido.
-   Validación de campos obligatorios y del formato del teléfono.
-   Menú interactivo con cinco tipos de pizza.
-   Selección de varias pizzas y acumulación de cantidades en un mismo
    pedido.
-   Validación de cantidades y disponibilidad de stock.
-   Descuento del **10 %** cuando el pedido contiene más de tres pizzas.
-   Descuento adicional del **5 %** para clientes frecuentes.
-   Confirmación o cancelación del pedido.
-   Actualización del inventario únicamente cuando se confirma un pedido
    válido.
-   Resumen final con los datos del cliente, los productos, los
    descuentos, el total, el estado del pedido y el repartidor asignado.

## Tecnologías y requisitos

-   **Lenguaje:** Kotlin
-   **Entorno de desarrollo:** IntelliJ IDEA
-   **JDK:** 21
-   **Tipo de aplicación:** consola

No se requiere una base de datos ni dependencias externas para ejecutar
esta versión.

## Menú de pizzas de ejemplo

    Opción Pizza             Precio de ejemplo   Stock inicial
  -------- --------------- ------------------- ---------------
         1 Margarita                   \$6.990              10
         2 Pepperoni                   \$8.990              10
         3 Cuatro Quesos               \$9.990              10
         4 Hawaiana                    \$8.490              10
         5 Vegetariana                 \$7.990              10

Los precios y las cantidades iniciales son valores de ejemplo definidos
en el código y pueden modificarse.

## Reglas de descuentos

Los descuentos se calculan sobre el subtotal del pedido:

-   **Descuento por cantidad:** 10 % si se compran más de tres pizzas.
-   **Descuento por cliente frecuente:** 5 % si se responde `S` al
    indicar si el cliente es frecuente.
-   Los descuentos son acumulables y se suman para obtener el descuento
    total.

**Ejemplo:** para cuatro pizzas Margarita, el subtotal es \$27.960. Si
el cliente es frecuente, se aplica un 10 % (\$2.796) y un 5 % (\$1.398),
para un total de \$23.766.

## Cómo ejecutar el proyecto

1.  Abre IntelliJ IDEA.
2.  Selecciona **Open** y elige la carpeta del proyecto `Pizzeria`.
3.  Comprueba que el proyecto utilice JDK 21.
4.  Abre el archivo `Main.kt`.
5.  Ejecuta la función `main` mediante el botón verde de ejecución o con
    `Ctrl + Shift + F10`.
6.  Sigue las instrucciones que aparecen en la consola.

## Flujo de uso

1.  Introduce el nombre, la dirección, el teléfono y el ID del pedido.
2.  Indica si el cliente es frecuente (`S` o `N`).
3.  Selecciona una pizza del menú e introduce una cantidad.
4.  Repite la selección para agregar otros productos.
5.  Elige `0` para finalizar y confirmar el pedido, o `9` para
    cancelarlo.
6.  Revisa el resumen que muestra el programa.

El stock se consulta durante la selección y se descuenta cuando se
confirma un pedido válido. Si se cancela el pedido o no se agregan
productos, no se modifica el inventario.

## Pruebas recomendadas

  -----------------------------------------------------------------------
  Prueba                              Resultado esperado
  ----------------------------------- -----------------------------------
  Introducir un nombre vacío          El programa vuelve a solicitar el
                                      dato.

  Introducir un teléfono inválido     El programa vuelve a solicitar el
                                      teléfono.

  Introducir una cantidad no numérica El programa solicita una cantidad
  o menor que uno                     válida.

  Solicitar más unidades que el stock La selección se rechaza y el
  disponible                          carrito no cambia.

  Comprar exactamente tres pizzas     No se aplica el descuento por
                                      cantidad.

  Comprar cuatro pizzas               Se aplica el descuento por cantidad
                                      del 10 %.

  Indicar que el cliente es frecuente Se aplica el descuento adicional
                                      del 5 %.

  Cancelar con la opción `9`          El pedido queda cancelado y el
                                      stock no se modifica.

  Finalizar sin productos             No se confirma el pedido y el stock
                                      no se modifica.

  Confirmar un pedido válido          Se descuenta el inventario y se
                                      muestra el resumen.
  -----------------------------------------------------------------------

## Estructura del proyecto

La estructura exacta puede variar según las opciones elegidas al crear
el proyecto en IntelliJ IDEA. Como mínimo, conserva:

-   `Main.kt`: código fuente de la aplicación.
-   Archivos de configuración del proyecto generados por IntelliJ IDEA,
    si corresponden.

## Alcance y consideraciones

-   El inventario se mantiene en memoria y vuelve a sus valores
    iniciales al reiniciar el programa.
-   El stock se modela como unidades disponibles de cada tipo de pizza;
    no se gestionan ingredientes individuales.
-   La asignación del repartidor es fija (`Repartidor 1`) cuando el
    pedido se confirma.
-   Los pedidos no se guardan en una base de datos.
-   La aplicación es una simulación académica de consola, no una
    aplicación móvil con interfaz gráfica.

## Entrega

Comprime la carpeta del proyecto en formato **ZIP o RAR** y sube el
archivo a la plataforma de la asignatura, según las instrucciones de la
actividad.

------------------------------------------------------------------------

Proyecto académico --- PMY2201 Desarrollo de Aplicaciones Móviles,
Semana 1.
