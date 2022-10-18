package com.example.orgs.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.orgs.R
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.ui.database.AppDatabase
import com.example.orgs.ui.dialog.FormularioImagemDialog
import com.example.orgs.ui.extensions.tryLoadImage
import com.example.orgs.ui.modelo.Produtos
import com.example.orgs.ui.modelo.Usuario
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import java.math.BigDecimal

class FormularioProdutoActivity : UsuarioBaseActivity() {

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
        tentaBuscarProduto()
    }

    private fun tentaBuscarProduto() {
        lifecycleScope.launch {
            produtosDao.buscaId(produtoId).collect {
                it?.let {
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
            lifecycleScope.launch {
                usuario.firstOrNull()?.let{ usuario -> //lembrando que o usuario é um stateFlow, então podemos suar o value
                    produtosDao.adiciona(criaProduto(usuario.id))
                    finish()
                }
            }
        }
    }

    private fun criaProduto(usuarioId: String): Produtos {
        val campoNome = binding.nome.text.toString()
        val campoDescricao = binding.descricao.text.toString()
        val campoValor = validacaoValor(binding.valor.text.toString())
        return Produtos(
            id = produtoId,
            nome = campoNome,
            descricao = campoDescricao,
            valor = campoValor,
            imagemUrl = url,
            usuarioId = usuarioId
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