package com.example.fifo_simulador.resultados.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fifo_simulador.MainModel
import com.example.fifo_simulador.resultados.model.ResultadosModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ResultadosViewModel : ViewModel() {
    val progresoSimulacion = MutableLiveData<Float>()
    val resultados = MutableLiveData<ResultadosModel>()
    val procesoEjecutando = MutableLiveData<String>()

    fun ejecutarProcesos(procesos: List<MainModel>) {
        viewModelScope.launch(Dispatchers.Main) {
            var tiempoTotal = 0f
            var tiempoEsperaTotal = 0f


            for (proceso in procesos) {
                procesoEjecutando.value = "Ejecutando ${proceso.nombre}"


                val tiempoEspera = tiempoTotal - proceso.tiempoLlegada
                if (tiempoEspera > 0) {
                    tiempoEsperaTotal += tiempoEspera
                } else {
                    tiempoEsperaTotal += 0
                }
                tiempoTotal += proceso.tiempoEjecucion
                for (i in 0..100 step 10) {
                    progresoSimulacion.value = i.toFloat()
                    delay((proceso.tiempoEjecucion * 1000 / 10).toLong())
                }
            }

            val tiempoEsperaPromedio = if (procesos.isNotEmpty()) {
                tiempoEsperaTotal / procesos.size
            } else {
                0f
            }

            resultados.value = ResultadosModel(tiempoTotal = tiempoTotal, tiempoEsperaPromedio = tiempoEsperaPromedio, )
        }
    }
}
