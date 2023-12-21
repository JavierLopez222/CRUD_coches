package com.example.crud_coches

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class CocheAdaptorClubAdaptador(private val lista_coche: MutableList<Coche>):
    RecyclerView.Adapter<CocheAdaptor.ClubViewHolder>(), Filterable {
    private lateinit var contexto: Context
    private var lista_filtrada = lista_coche


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CocheAdaptor.CocheViewHolder {
        val vista_item = LayoutInflater.from(parent.context).inflate(R.layout.item_coche,parent,false)
        contexto = parent.context
        return CocheViewHolder(vista_item)
    }

    override fun onBindViewHolder(holder: CocheAdaptor.CocheViewHolder, position: Int) {
        val item_actual = lista_filtrada[position]
        holder.marca.text = item_actual.marca
        holder.modelo.text = item_actual.modelo
        holder.matricula.text = item_actual.matricula.toString()


    }

    override fun getItemCount(): Int = lista_filtrada.size

    class ClubViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val marca: TextView = itemView.findViewById(R.id.item_marca)
        val modelo: TextView = itemView.findViewById(R.id.item_modelo)
        val matricula: TextView = itemView.findViewById(R.id.item_matricula)

    }

    override fun getFilter(): Filter {
        return  object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val busqueda = p0.toString().lowercase()
                if (busqueda.isEmpty()){
                    lista_filtrada = lista_coche
                }else {
                    lista_filtrada = (lista_coche.filter {
                        it.marca.toString().lowercase().contains(busqueda)
                    }) as MutableList<Coche>
                }

                val filterResults = FilterResults()
                filterResults.values = lista_filtrada
                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                notifyDataSetChanged()
            }

        }
    }
}