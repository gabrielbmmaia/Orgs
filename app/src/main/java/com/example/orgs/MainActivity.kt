package com.example.orgs

import android.app.Activity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class MainActivity: Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val nome = findViewById<TextView>(R.id.titulo)
        nome.text = "Cesta de Frutas"
        val descricao = findViewById<TextView>(R.id.descricao)
        descricao.text = "Laranja, morango, maçã"
        val valor = findViewById<TextView>(R.id.valor)
        valor.text = "R$ 19.99"

    }
}