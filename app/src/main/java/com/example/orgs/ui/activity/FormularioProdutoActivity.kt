package com.example.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.example.orgs.R
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.databinding.ForumlarioImagemBinding
import com.example.orgs.ui.modelo.Produtos
import com.example.orgs.ui.dao.ProdutosDao
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
        binding.fomularioProdutoImagem.setOnClickListener {
            var bindingFormularioImagem = ForumlarioImagemBinding.inflate(layoutInflater)
            bindingFormularioImagem.formularioImagemBotaoCarregar.setOnClickListener{
                var url = bindingFormularioImagem.url.text.toString()
                bindingFormularioImagem.formularioImagemImageview.load(url){
                    placeholder(R.drawable.placeholder)
                }
            }
            AlertDialog.Builder(this)
                .setView(bindingFormularioImagem.root)
                .setPositiveButton("Confirmar") { _, _ ->
                    url = bindingFormularioImagem.url.text.toString()
                    binding.fomularioProdutoImagem.load(url)
                }
                .setNegativeButton("Cancelar") { _, _ ->
                }
                .show()
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