package com.example.fifo_simulador

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel: ViewModel() {
    private val _procesos = MutableLiveData<MutableList<MainModel>>(mutableListOf())
    val procesos: LiveData<MutableList<MainModel>> get() = _procesos


    fun agregarProceso(nombre: String, tiempoEjecucion: Float, tiempoLlegada: Float) {
        val proceso = MainModel(nombre, tiempoEjecucion, tiempoLlegada)
        _procesos.value?.add(proceso)
        _procesos.value = _procesos.value
    }
}
