package com.example.fifo_simulador

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fifo_simulador.resultados.view.ResultadosView

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: ProcesoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Variables
        val nombreProceso = findViewById<EditText>(R.id.editTextNombre)
        val tiempoProceso = findViewById<EditText>(R.id.editTextTiempo)
        val agregarProceso = findViewById<Button>(R.id.btnAgregar)
        val iniciarProceso = findViewById<Button>(R.id.btnIniciar)
        val tiempoLlegada = findViewById<EditText>(R.id.editTextTiempoLlegada)
        val recyclerViewProcesos = findViewById<RecyclerView>(R.id.recyclerViewProcesos)

        val textView = findViewById<TextView>(R.id.txtTittle)

        val paint = textView.paint
        val width = paint.measureText(textView.text.toString())

        val shader = LinearGradient(
            0f, 0f, width, textView.textSize,
            ContextCompat.getColor(this, R.color.gradient_text_uno),
            ContextCompat.getColor(this, R.color.gradient_text_dos),
            Shader.TileMode.CLAMP
        )

        textView.paint.shader = shader

        recyclerViewProcesos.layoutManager = LinearLayoutManager(this)
        adapter = ProcesoAdapter(mutableListOf())
        recyclerViewProcesos.adapter = adapter

        mainViewModel.procesos.observe(this, Observer { procesos ->
            adapter = ProcesoAdapter(procesos)
            recyclerViewProcesos.adapter = adapter
        })

        agregarProceso.setOnClickListener {
            val nombre = nombreProceso.text.toString()
            val tiempoEjecucion = tiempoProceso.text.toString().toFloatOrNull()
            val tiempoLLegada = tiempoLlegada.text.toString().toFloatOrNull()

            if (nombre.isNotBlank() && tiempoEjecucion != null) {
                if (tiempoLLegada != null) {
                    mainViewModel.agregarProceso(nombre, tiempoEjecucion, tiempoLLegada)
                }
                nombreProceso.text.clear()
                tiempoProceso.text.clear()
                tiempoLlegada.text.clear()
            } else {
                Toast.makeText(this, "Error al agregar el proceso", Toast.LENGTH_SHORT).show()
            }
        }

        iniciarProceso.setOnClickListener {
            val procesos = mainViewModel.procesos.value

            if (!procesos.isNullOrEmpty()) {
                val intent = Intent(this, ResultadosView::class.java)
                intent.putParcelableArrayListExtra("procesos", ArrayList(procesos))
                startActivity(intent)
            } else {
                Toast.makeText(this, "No hay procesos para simular", Toast.LENGTH_SHORT).show()
            }
        }




    }
}
