package com.example.crud_coches

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var crear: Button
    lateinit var ver: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        crear = findViewById(R.id.btn_crear)
        ver = findViewById(R.id.btn_ver)

        crear.setOnClickListener{
            var intent_crear = Intent(applicationContext, crearCoche::class.java)
            startActivity(intent_crear)
        }

        ver.setOnClickListener{
            var intent_ver = Intent(applicationContext, verCoche::class.java)
            startActivity(intent_ver)
        }

    }
}