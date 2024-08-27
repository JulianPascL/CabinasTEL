import kotlin.system.exitProcess

fun main() {
    val control = ControlGastosTelefonicos()

    while (true) {
        println("\n--- BIENVENIDO ---")
        println("\n")
        println("1. Registrar llamada")
        println("2. Mostrar información del tipo de llamada")
        println("3. Mostrar consolidado total")
        println("4. Reiniciar tipo de llamada")
        println("5. Salir")
        print("Seleccione una opción: ")

        when (readLine()?.toIntOrNull()) {
            1 -> {
                println("\nSeleccione el tipo de llamada para la creación de la cabina:")
                println("1. Local")
                println("2. Larga Distancia")
                println("3. Celular")
                print("Ingrese su elección: ")
                when (readLine()?.toIntOrNull()) {
                    1 -> control.registrarLlamada(TipoCabina.LOCAL)
                    2 -> control.registrarLlamada(TipoCabina.LARGA_DISTANCIA)
                    3 -> control.registrarLlamada(TipoCabina.CELULAR)
                    else -> {
                        println("Opción no válida.")
                        continue
                    }
                }
                println("Llamada registrada con éxito!")
            }
            2 -> {
                println("\nSeleccione el tipo de llamada para ver información:")
                println("1. Local")
                println("2. Larga Distancia")
                println("3. Celular")
                print("Ingrese su elección: ")
                val tipoCabina = when (readLine()?.toIntOrNull()) {
                    1 -> TipoCabina.LOCAL
                    2 -> TipoCabina.LARGA_DISTANCIA
                    3 -> TipoCabina.CELULAR
                    else -> {
                        println("Opción no válida.")
                        continue
                    }
                }
                val info = control.obtenerInformacionCabina(tipoCabina)
                println("\nInformación del tipo de llamada ${info.tipo}:")
                println("Total de llamadas: ${info.totalLlamadas}")
                println("Duración total: ${info.duracionTotal} minutos")
                println("Costo total: $${String.format("%.2f", info.costoTotal)}")
                if (info.duracionTotal > 0) {
                    val costoPorMinuto = info.costoTotal / info.duracionTotal
                    println("Costo por minuto: $${String.format("%.2f", costoPorMinuto)}")
                }
            }
            3 -> {
                val consolidado = control.obtenerConsolidadoTotal()
                println("\nConsolidado Total:")
                var cabinasCreadasCount = 0
                consolidado.forEach { info ->
                    if (info.totalLlamadas > 0) {
                        cabinasCreadasCount++
                        println("\nTipo comunicación ${info.tipo}:")
                        println("Total de llamadas: ${info.totalLlamadas}")
                        println("Duración total: ${info.duracionTotal} minutos")
                        println("Costo total: $${String.format("%.2f", info.costoTotal)}")
                        if (info.duracionTotal > 0) {
                            val costoPorMinuto = info.costoTotal / info.duracionTotal
                            println("Costo por minuto: $${String.format("%.2f", costoPorMinuto)}")
                        }
                    }
                }
                println("\nResumen General:")
                println("Lineas de comunicación utilizadas: $cabinasCreadasCount")
                val totalLlamadas = consolidado.sumOf { it.totalLlamadas }
                val duracionTotal = consolidado.sumOf { it.duracionTotal }
                val costoTotal = consolidado.sumOf { it.costoTotal }
                println("Cabinas creadas: $totalLlamadas")
                println("Duración total: $duracionTotal minutos")
                println("Costo total: $${String.format("%.2f", costoTotal)}")
                if (duracionTotal > 0) {
                    val costoPromedioGeneral = costoTotal / duracionTotal
                    println("Costo promedio por minuto: $${String.format("%.2f", costoPromedioGeneral)}")
                }
            }
            4 -> {
                println("\nSeleccione el tipo de linea a reiniciar:")
                println("1. Local")
                println("2. Larga Distancia")
                println("3. Celular")
                print("Ingrese su elección: ")
                val tipoCabina = when (readLine()?.toIntOrNull()) {
                    1 -> TipoCabina.LOCAL
                    2 -> TipoCabina.LARGA_DISTANCIA
                    3 -> TipoCabina.CELULAR
                    else -> {
                        println("Opción no válida.")
                        continue
                    }
                }
                control.reiniciarCabina(tipoCabina)
                println("Linea ${tipoCabina} reiniciada.")
            }
            5 -> {
                println("Hasta luego!")
                exitProcess(0)
            }
            else -> println("Opción no válida. Por favor, intente de nuevo.")
        }
    }
}