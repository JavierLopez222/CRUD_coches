package com.example.crud_coches

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class crearCoche : AppCompatActivity(), CoroutineScope {

    lateinit var marca: EditText
    lateinit var modelo: EditText
    lateinit var matricula: EditText
    lateinit var crearCoche: Button
    lateinit var volver: Button

    private lateinit var db_ref: DatabaseReference
    private lateinit var st_ref: StorageReference
    private lateinit var lista_coches:  MutableList<Coche>

    private lateinit var job: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crear_coche)

        val this_activity = this
        job = Job()

        marca = findViewById(R.id.marca)
        modelo = findViewById(R.id.modelo)
        matricula = findViewById(R.id.matricula)
        crearCoche = findViewById(R.id.crearCoche)
        volver = findViewById(R.id.volver)


        db_ref = FirebaseDatabase.getInstance().getReference()
        st_ref = FirebaseStorage.getInstance().getReference()
        lista_coches = Utilidades.obtenerListaCoches(db_ref)

        crearCoche.setOnClickListener{
            if (marca.text.toString().trim().isEmpty() ||
                modelo.text.toString().trim().isEmpty() ||
                matricula.text.toString().trim().isEmpty()
            ) {
                Toast.makeText(
                    applicationContext, "Faltan datos en el " +
                            "formularion", Toast.LENGTH_SHORT
                ).show()

            } else if (Utilidades.existeCoche(coches , marca.text.toString().trim())) {
                Toast.makeText(applicationContext, "Ese Club ya existe", Toast.LENGTH_SHORT)
                    .show()
            } else{
                var id_generado: String? = db_ref.child("concesionario").child("coches").push().key

                launch {

                    Utilidades.escribirCoche(
                        db_ref, id_generado!!,
                        marca.text.toString().trim(),
                        modelo.text.toString().trim(),
                        matricula.text.toString().trim()
                    )
                    Utilidades.tostadaCorrutina(
                        this_activity,
                        applicationContext,
                        "Coche creado con exito"
                    )
                    val activity = Intent(applicationContext, MainActivity::class.java)
                    startActivity(activity)
                }

            }

        volver.setOnClickListener{
            var intentvolver = Intent(applicationContext, MainActivity::class.java)
            startActivity(intentvolver)
        }

    }
        override fun onDestroy() {
            job.cancel()
            super.onDestroy()
        }
        override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO + job
}