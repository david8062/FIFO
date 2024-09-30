package com.example.fifo_simulador.resultados.view

import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.example.fifo_simulador.MainModel
import com.example.fifo_simulador.R
import com.example.fifo_simulador.resultados.viewModel.ResultadosViewModel

class ResultadosView : AppCompatActivity() {

    private val resultadosViewModel: ResultadosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados_view)

        val textViewEjecutando = findViewById<TextView>(R.id.textViewEjecutando)
        val textViewTiempoTranscurrido = findViewById<TextView>(R.id.textViewTiempoTranscurrido)
        val textViewTiempoTotal = findViewById<TextView>(R.id.textViewTiempoTotal)
        val textViewTiempoEsperaPromedio = findViewById<TextView>(R.id.textViewTiempoEsperaPromedio)
        val progressBarSimulacion = findViewById<ProgressBar>(R.id.progressBarSimulacion)
        val textSimulacion = findViewById<TextView>(R.id.textViewSimulacion)
        val textResultados = findViewById<TextView>(R.id.textViewResultados)
        val paint = textSimulacion.paint
        val paint2 = textResultados.paint
        val widthSimulacion = paint.measureText(textSimulacion.text.toString())
        val widthResultados = paint2.measureText(textResultados.text.toString())


        val shaderSimulacion = LinearGradient(
            0f, 0f, widthSimulacion, textSimulacion.textSize,
            ContextCompat.getColor(this, R.color.gradient_text_uno),
            ContextCompat.getColor(this, R.color.gradient_text_dos),
            Shader.TileMode.CLAMP
        )
        textSimulacion.paint.shader = shaderSimulacion


        val shaderResultados = LinearGradient(
            0f, 0f, widthResultados, textResultados.textSize,
            ContextCompat.getColor(this, R.color.gradient_text_uno),
            ContextCompat.getColor(this, R.color.gradient_text_dos),
            Shader.TileMode.CLAMP
        )
        textResultados.paint.shader = shaderResultados


        resultadosViewModel.progresoSimulacion.observe(this, Observer { progreso ->
            progressBarSimulacion.progress = progreso.toInt()
            textViewTiempoTranscurrido.text = "Tiempo transcurrido: ${progreso.toInt()}%"
        })

        resultadosViewModel.resultados.observe(this, Observer { resultados ->
            textViewTiempoTotal.text = "Tiempo Total de Ejecución: ${resultados.tiempoTotal} segundos"
            textViewTiempoEsperaPromedio.text = "Tiempo de Espera Promedio: ${resultados.tiempoEsperaPromedio} segundos"
        })

        resultadosViewModel.procesoEjecutando.observe(this, Observer { proceso ->
            textViewEjecutando.text = proceso
        })

        val procesos: ArrayList<MainModel>? = intent.getParcelableArrayListExtra("procesos")

        if (procesos != null) {
            resultadosViewModel.ejecutarProcesos(procesos)
        } else {
            textViewEjecutando.text = "No se recibieron procesos para ejecutar."
        }
    }
}
