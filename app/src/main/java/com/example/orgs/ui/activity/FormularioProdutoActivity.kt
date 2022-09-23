package com.example.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.ui.modelo.Produtos
import com.example.orgs.ui.modelo.ProdutosDao
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity(R.layout.activity_formulario_produto) {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoSalvar()
    }

    private fun criaProduto(): Produtos {

        val campoNome = binding.nome.text.toString()
        val campoDescricao = binding.descricao.text.toString()
        val campoValor = binding.valor.text.toString()

        val validacaoValor = if (campoValor.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(campoValor)
        }

        return Produtos(
            nome = campoNome,
            descricao = campoDescricao,
            valor = validacaoValor
        )
    }

    private fun configuraBotaoSalvar() {
        val button = binding.buttonSalvar
        button.setOnClickListener {
            val novoProduto = criaProduto()
            val dao = ProdutosDao()
            dao.adiciona(novoProduto)
            finish()
        }
    }


}