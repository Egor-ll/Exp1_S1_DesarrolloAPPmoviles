
data class Pizza(
    val nombre: String,
    val precio: Double,
    var stock: Int
)

data class DetallePedido(
    val pizza: Pizza,
    var cantidad: Int
)

// Solicita un dato obligatorio y vuelve a preguntar si esta vacio.
fun leerDatoObligatorio(mensaje: String): String {
    while (true) {
        print(mensaje)
        val dato = readlnOrNull()?.trim()

        if (!dato.isNullOrEmpty()) {
            return dato
        }

        println("El dato es obligatorio. Intente nuevamente.")
    }
}

// Valida el telefono antes de continuar.
fun leerTelefono(): String {
    while (true) {
        print("Telefono (8 a 15 caracteres validos): ")
        val telefono = readlnOrNull()?.trim()

        if (
            !telefono.isNullOrEmpty() &&
            telefono.matches(Regex("[0-9+() -]{8,15}")) &&
            telefono.count { it.isDigit() } >= 8
        ) {
            return telefono
        }

        println("Telefono invalido. Ingrese un numero valido.")
    }
}

// Solicita una cantidad entera positiva.
fun leerCantidad(mensaje: String): Int {
    while (true) {
        print(mensaje)
        val cantidad = readlnOrNull()?.trim()?.toIntOrNull()

        if (cantidad != null && cantidad > 0) {
            return cantidad
        }

        println("Ingrese un numero entero mayor que cero.")
    }
}

// Solicita una respuesta S o N.
fun leerRespuestaSiNo(mensaje: String): Boolean {
    while (true) {
        print(mensaje)
        when (readlnOrNull()?.trim()?.uppercase()) {
            "S" -> return true
            "N" -> return false
            else -> println("Respuesta no valida. Ingrese S o N.")
        }
    }
}

fun main() {

    // 1. MENU E INVENTARIO


    val menuPizzas = listOf(
        Pizza("Margarita", 6990.0, 10),
        Pizza("Pepperoni", 8990.0, 10),
        Pizza("Cuatro Quesos", 9990.0, 10),
        Pizza("Hawaiana", 8490.0, 10),
        Pizza("Vegetariana", 7990.0, 10)
    )

    val carrito = mutableListOf<DetallePedido>()

    println("======================================")
    println("          PIZZERIA KOTLIN")
    println("======================================")


    // 2. REGISTRO Y VALIDACION DEL CLIENTE


    val nombre = leerDatoObligatorio("Nombre del cliente: ")
    val direccion = leerDatoObligatorio("Direccion de entrega: ")
    val telefono = leerTelefono()
    val idPedido = leerDatoObligatorio("ID del pedido: ")

    val clienteFrecuente = leerRespuestaSiNo(
        "Es cliente frecuente? (S/N): "
    )

    // 3. SELECCION DE PIZZAS
    // El stock no se modifica en esta etapa.

    var continuarComprando = true
    var pedidoCancelado = false

    while (continuarComprando) {
        println("\n============== MENU DE PIZZAS ==============")

        menuPizzas.forEachIndexed { indice, pizza ->
            val cantidadEnCarrito = carrito
                .find { it.pizza == pizza }
                ?.cantidad ?: 0

            val stockDisponible = pizza.stock - cantidadEnCarrito

            println(
                "${indice + 1}. ${pizza.nombre} - " +
                        "$${"%.0f".format(pizza.precio)} " +
                        "| Disponible: $stockDisponible"
            )
        }

        println("0. Finalizar y confirmar pedido")
        println("9. Cancelar pedido")
        print("Seleccione una opcion: ")

        val opcion = readlnOrNull()?.trim()?.toIntOrNull()

        when {
            opcion == 0 -> {
                continuarComprando = false
            }

            opcion == 9 -> {
                pedidoCancelado = true
                continuarComprando = false
                carrito.clear()
                println("Pedido cancelado. No se modifico el inventario.")
            }

            opcion == null || opcion !in 1..menuPizzas.size -> {
                println("Opcion no valida. Intente nuevamente.")
            }

            else -> {
                val pizzaSeleccionada = menuPizzas[opcion - 1]

                val cantidadEnCarrito = carrito
                    .find { it.pizza == pizzaSeleccionada }
                    ?.cantidad ?: 0

                val stockDisponible =
                    pizzaSeleccionada.stock - cantidadEnCarrito

                if (stockDisponible <= 0) {
                    println("No hay unidades disponibles de esta pizza.")
                } else {
                    val cantidad = leerCantidad(
                        "Cantidad de ${pizzaSeleccionada.nombre}: "
                    )

                    if (cantidad > stockDisponible) {
                        println(
                            "Stock insuficiente. " +
                                    "Unidades disponibles: $stockDisponible"
                        )
                    } else {
                        val detalleExistente = carrito.find {
                            it.pizza == pizzaSeleccionada
                        }

                        if (detalleExistente != null) {
                            detalleExistente.cantidad += cantidad
                        } else {
                            carrito.add(
                                DetallePedido(
                                    pizza = pizzaSeleccionada,
                                    cantidad = cantidad
                                )
                            )
                        }

                        println(
                            "$cantidad unidad(es) de " +
                                    "${pizzaSeleccionada.nombre} agregada(s)."
                        )
                    }
                }
            }
        }
    }

    // 4. VALIDACION Y CONFIRMACION DEL PEDIDO

    var pedidoAceptado = false

    if (pedidoCancelado) {
        println("\nEl pedido fue cancelado.")
    } else if (carrito.isEmpty()) {
        println("\nNo se puede confirmar un pedido sin productos.")
    } else {
        // Comprobar nuevamente el stock antes de confirmar.
        val stockSuficiente = carrito.all { detalle ->
            detalle.cantidad <= detalle.pizza.stock
        }

        if (stockSuficiente) {
            // Solo se descuenta el inventario al confirmar.
            carrito.forEach { detalle ->
                detalle.pizza.stock -= detalle.cantidad
            }

            pedidoAceptado = true
            println("\nStock verificado. Pedido confirmado.")
        } else {
            println("\nNo se puede confirmar el pedido.")
            println("No hay stock suficiente.")
            println("El inventario no fue modificado.")
        }
    }

    // 5. CALCULO DEL PEDIDO Y DESCUENTOS

    val cantidadTotalPizzas = if (pedidoAceptado) {
        carrito.sumOf { it.cantidad }
    } else {
        0
    }

    val subtotal = if (pedidoAceptado) {
        carrito.sumOf { it.pizza.precio * it.cantidad }
    } else {
        0.0
    }

    val descuentoCantidad = if (
        pedidoAceptado && cantidadTotalPizzas > 3
    ) {
        subtotal * 0.10
    } else {
        0.0
    }

    val descuentoFrecuente = if (
        pedidoAceptado && clienteFrecuente
    ) {
        subtotal * 0.05
    } else {
        0.0
    }

    val descuentoTotal = descuentoCantidad + descuentoFrecuente
    val totalPedido = subtotal - descuentoTotal

    // 6. ESTADO Y REPARTIDOR

    val estadoPedido = if (pedidoAceptado) {
        "Preparacion"
    } else if (pedidoCancelado) {
        "Cancelado"
    } else {
        "No confirmado"
    }

    val repartidorAsignado = if (pedidoAceptado) {
        "Repartidor 1"
    } else {
        "Sin asignar"
    }


    // 7. RESUMEN FINAL

    println("\n======================================")
    println("          RESUMEN DEL PEDIDO")
    println("======================================")

    println("\nDATOS DEL CLIENTE")
    println("Nombre: $nombre")
    println("Direccion: $direccion")
    println("Telefono: $telefono")
    println(
        "Cliente frecuente: ${if (clienteFrecuente) "Si" else "No"}"
    )

    println("\nDETALLE DE PRODUCTOS")

    if (carrito.isEmpty()) {
        println("No hay productos en el pedido.")
    } else {
        carrito.forEach { detalle ->
            val importe = detalle.pizza.precio * detalle.cantidad

            println(
                "${detalle.pizza.nombre} x ${detalle.cantidad} = " +
                        "$${"%.0f".format(importe)}"
            )
        }
    }

    println("\nRESUMEN ECONOMICO")
    println("ID del pedido: $idPedido")
    println("Cantidad total: $cantidadTotalPizzas pizzas")
    println("Subtotal: $${"%.0f".format(subtotal)}")
    println("Descuento por cantidad: -$${"%.0f".format(descuentoCantidad)}")
    println("Descuento cliente frecuente: -$${"%.0f".format(descuentoFrecuente)}")
    println("Descuento total: -$${"%.0f".format(descuentoTotal)}")
    println("TOTAL A PAGAR: $${"%.0f".format(totalPedido)}")

    println("\nESTADO Y ENTREGA")
    println("Estado: $estadoPedido")
    println("Repartidor: $repartidorAsignado")

    println("\nSTOCK FINAL")

    menuPizzas.forEach { pizza ->
        println("${pizza.nombre}: ${pizza.stock} unidades")
    }

    println("\n======================================")
    println("             FIN DEL PEDIDO")
    println("======================================")
}
