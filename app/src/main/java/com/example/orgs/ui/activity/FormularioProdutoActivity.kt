package com.example.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.ui.database.AppDatabase
import com.example.orgs.ui.dialog.FormularioImagemDialog
import com.example.orgs.ui.extensions.tryLoadImage
import com.example.orgs.ui.modelo.Produtos
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity(R.layout.activity_formulario_produto) {

    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }

    private var url: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraBotaoSalvar()
        title = "Cadastrar Produto"
        binding.fomularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this)
                .mostra (url){ imagem ->
                    url = imagem
                    binding.fomularioProdutoImagem.tryLoadImage(url)
                }
        }
    }

    private fun configuraBotaoSalvar() {

        val button = binding.buttonSalvar
        val db = AppDatabase.instance(this)
        val produtosDao = db.produtosDao()
        button.setOnClickListener {
            val novoProduto = criaProduto()
            produtosDao.adiciona(novoProduto)
            finish()
        }
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
            valor = validacaoValor,
            imagemUrl = url
        )
    }
}