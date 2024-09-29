package com.example.fifo_simulador

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adapter: ProcesoAdapter // Declara la variable adapter aquí

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //Variables
        val nombreProceso = findViewById<EditText>(R.id.editTextNombre)
        val tiempoProceso = findViewById<EditText>(R.id.editTextTiempo)
        val agregarProceso = findViewById<Button>(R.id.btnAgregar)
        val recyclerViewProcesos = findViewById<RecyclerView>(R.id.recyclerViewProcesos)

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

            if (nombre.isNotBlank() && tiempoEjecucion != null) {
                mainViewModel.agregarProceso(nombre, tiempoEjecucion)
                nombreProceso.text.clear()
                tiempoProceso.text.clear()
            } else {
                Toast.makeText(this, "Error al agregar el proceso", Toast.LENGTH_SHORT).show()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
