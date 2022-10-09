package com.example.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.ui.database.AppDatabase
import com.example.orgs.ui.dialog.FormularioImagemDialog
import com.example.orgs.ui.extensions.tryLoadImage
import com.example.orgs.ui.modelo.Produtos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class FormularioProdutoActivity : AppCompatActivity(R.layout.activity_formulario_produto) {

    private val scope = CoroutineScope(IO)
    private var produtoId = 0L
    private var url: String? = null
    private val binding by lazy {
        ActivityFormularioProdutoBinding.inflate(layoutInflater)
    }
    private val produtosDao by lazy {
        AppDatabase.instance(this).produtosDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        title = "Cadastrar Produto"
        configuraBotaoSalvar()
        alterarImagem()
        procurarIdProduto()
    }

    override fun onResume() {
        super.onResume()
        scope.launch {
            produtosDao.buscaId(produtoId)?.let {
                withContext(Main){
                    title = "Alterando ${it.nome}"
                    preencheCampos(it)
                }
            }
        }
    }

    private fun alterarImagem() {
        binding.fomularioProdutoImagem.setOnClickListener {
            FormularioImagemDialog(this)
                .mostra(url) { imagem ->
                    url = imagem
                    binding.fomularioProdutoImagem.tryLoadImage(url)
                }
        }
    }

    private fun procurarIdProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    private fun preencheCampos(produtosCarregados: Produtos) {
        url = produtosCarregados.imagemUrl
        binding.fomularioProdutoImagem.tryLoadImage(produtosCarregados.imagemUrl)
        binding.nome.setText(produtosCarregados.nome)
        binding.descricao.setText(produtosCarregados.descricao)
        binding.valor.setText(produtosCarregados.valor.toPlainString())
    }

    private fun configuraBotaoSalvar() {
        val button = binding.buttonSalvar
        button.setOnClickListener {
            scope.launch {
                produtosDao.adiciona(criaProduto())
                finish()
            }
        }
    }

    private fun criaProduto(): Produtos {
        val campoNome = binding.nome.text.toString()
        val campoDescricao = binding.descricao.text.toString()
        val campoValor = validacaoValor(binding.valor.text.toString())
        return Produtos(
            id = produtoId,
            nome = campoNome,
            descricao = campoDescricao,
            valor = campoValor,
            imagemUrl = url
        )
    }

    private fun validacaoValor(valor: String): BigDecimal {
        return if (valor.isBlank()) {
            BigDecimal.ZERO
        } else {
            BigDecimal(valor)
        }
    }
}