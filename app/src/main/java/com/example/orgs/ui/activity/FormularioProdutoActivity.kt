package com.example.orgs.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.example.orgs.R
import com.example.orgs.ui.modelo.Produtos
import kotlinx.android.synthetic.main.activity_formulario_produto.*
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity(R.layout.activity_formulario_produto) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val button = findViewById<Button>(R.id.button_salvar)
        button.setOnClickListener {
            val campoNome = findViewById<EditText>(R.id.nome).text.toString()
            val campoDescricao = findViewById<EditText>(R.id.descricao).text.toString()

            val campoValor = findViewById<EditText>(R.id.valor).text.toString()

            val validacaoValor = if (campoValor.isBlank()) {
                BigDecimal.ZERO
            } else {
                BigDecimal(campoValor)
            }

            val novoProduto = Produtos(
                nome = campoNome,
                descricao = campoDescricao,
                valor = validacaoValor
            )

            Log.i("FormularioProduto", "onCreate: $novoProduto")
        }
    }
}