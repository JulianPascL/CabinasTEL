
import kotlin.random.Random

enum class TipoCabina {
    LOCAL, LARGA_DISTANCIA, CELULAR  // Definicion de los tipo de lineas (Cabinas)

}

data class Llamada(
    val tipoCabina: TipoCabina,
    val duracion: Int // en minutos
)

class ControlGastosTelefonicos {
    private val llamadasPorTipo = mutableMapOf<TipoCabina, MutableList<Llamada>>()

    init {
        TipoCabina.values().forEach { llamadasPorTipo[it] = mutableListOf() }
    }

    fun registrarLlamada(tipoCabina: TipoCabina) {
        val duracionAleatoria = Random.nextInt(1, 51) // Genera un número aleatorio entre 1 y 50
        val nuevaLlamada = Llamada(tipoCabina, duracionAleatoria)
        llamadasPorTipo[tipoCabina]?.add(nuevaLlamada)
    }

    fun obtenerInformacionCabina(tipoCabina: TipoCabina): InformacionCabina {
        val llamadas = llamadasPorTipo[tipoCabina] ?: listOf()
        val totalLlamadas = llamadas.size
        val duracionTotal = llamadas.sumOf { it.duracion } // sumOf hace la suma de los valores de la lista
        val costoTotal = calcularCostoTotal(llamadas)
        return InformacionCabina(tipoCabina, totalLlamadas, duracionTotal, costoTotal)
    }

    fun obtenerConsolidadoTotal(): List<InformacionCabina> {
        return TipoCabina.values().map { obtenerInformacionCabina(it) } // map transforma la informacion
    }

    fun reiniciarCabina(tipoCabina: TipoCabina) {
        llamadasPorTipo[tipoCabina]?.clear()
    }

    private fun calcularCostoTotal(llamadas: List<Llamada>): Double {
        return llamadas.sumOf { llamada ->
            when (llamada.tipoCabina) {
                TipoCabina.LOCAL -> 50.0 * llamada.duracion
                TipoCabina.LARGA_DISTANCIA -> 350.0 * llamada.duracion
                TipoCabina.CELULAR -> 150.0 * llamada.duracion
            }
        }
    }
}

data class InformacionCabina(
    val tipo: TipoCabina,
    val totalLlamadas: Int,
    val duracionTotal: Int,
    val costoTotal: Double
)