package com.example.mascotaiot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador del RecyclerView para mostrar el historial de acciones.
 * Cada item muestra: Fecha, Hora, Usuario (RUT), Acción, Distancia.
 */
class AccionesAdapter(private val listaAcciones: List<AccionModel>) :
    RecyclerView.Adapter<AccionesAdapter.AccionViewHolder>() {

    inner class AccionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvFecha: TextView = itemView.findViewById(R.id.tvFecha)
        val tvHora: TextView = itemView.findViewById(R.id.tvHora)
        val tvUsuario: TextView = itemView.findViewById(R.id.tvUsuario)
        val tvAccion: TextView = itemView.findViewById(R.id.tvAccion)
        val tvDistancia: TextView = itemView.findViewById(R.id.tvDistancia)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_accion, parent, false)
        return AccionViewHolder(view)
    }

    override fun onBindViewHolder(holder: AccionViewHolder, position: Int) {
        val accion = listaAcciones[position]
        holder.tvFecha.text = accion.fecha
        holder.tvHora.text = accion.hora
        holder.tvUsuario.text = accion.usuario
        holder.tvAccion.text = accion.accion
        holder.tvDistancia.text = accion.distancia
    }

    override fun getItemCount(): Int = listaAcciones.size
}
