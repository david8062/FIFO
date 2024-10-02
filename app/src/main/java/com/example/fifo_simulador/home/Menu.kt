package com.example.fifo_simulador.home

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.fifo_simulador.MainActivity
import com.example.fifo_simulador.R

class Menu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        var actividad1 = findViewById<Button>(R.id.btnFIFO)
        var actividadEjemplo = findViewById<Button>(R.id.btnEjemplo)
        var actividadSimulador = findViewById<Button>(R.id.buttonSimulador)

        actividad1.setOnClickListener {
            val intent = Intent(this, activity1::class.java)
            startActivity(intent)
        }

        actividadEjemplo.setOnClickListener {
            val intent = Intent(this, activity2::class.java)
            startActivity(intent)
        }

        actividadSimulador.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menu)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}