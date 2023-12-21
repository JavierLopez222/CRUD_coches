package com.example.crud_coches

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Utilidades {
    companion object {

        fun existeCoche(coches: List<Coche>, marca:String):Boolean{
            return coches.any{ it.marca!!.lowercase()==marca.lowercase()}
        }

        fun obtenerListaCoches(db_ref: DatabaseReference):MutableList<Coche>{
            var lista = mutableListOf<Coche>()

            db_ref.child("concesionario")
                .child("coches")
                .addValueEvenListener(object : ValueEvenListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.children.forEach{hijo :DataSnapshot ->
                            val pojo_coche = hijo.getValue(Coche::class.java)
                            lista.add(pojo_coche!!)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        println(error.message)
                    }
                })
            return lista
        }

        fun escribirCoche(db_ref:DatabaseReference,id:String,marca:String,modelo:String,matricula:String,url_firebase:String)=
            db_ref.child("concesionario").child("coche").child(id).setValue(Coche(
                id,
                marca,
                modelo,
                matricula,
                url_firebase
            ))
        fun tostadaCorrutina(activity: AppCompatActivity, contexto: Context, texto:String){
            activity.runOnUiThread{
                Toast.makeText(
                    contexto,
                    texto,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}