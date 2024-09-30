package com.example.fifo_simulador
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProcesoAdapter(private val procesos: List<MainModel>) : RecyclerView.Adapter<ProcesoAdapter.ProcesoViewHolder>() {

    inner class ProcesoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewNombre: TextView = itemView.findViewById(R.id.textViewNombre)
        val textViewTiempo: TextView = itemView.findViewById(R.id.textViewTiempo)
        val textViewEstado: TextView = itemView.findViewById(R.id.textViewEstado)
        val textViewLlegada: TextView = itemView.findViewById(R.id.textViewLlegada)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProcesoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.procesos, parent, false)
        return ProcesoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProcesoViewHolder, position: Int) {
        val proceso = procesos[position]
        holder.textViewNombre.text = proceso.nombre
        holder.textViewTiempo.text = proceso.tiempoEjecucion.toString()
        holder.textViewEstado.text = "Pendiente"
        holder.textViewLlegada.text = proceso.tiempoLlegada.toString()
    }

    override fun getItemCount(): Int {
        return procesos.size
    }
}
