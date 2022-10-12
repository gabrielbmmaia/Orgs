package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.orgs.R
import com.example.orgs.databinding.ActivityDetalhesProdutoBinding
import com.example.orgs.ui.database.AppDatabase
import com.example.orgs.ui.extensions.formataParaMoedaReal
import com.example.orgs.ui.extensions.tryLoadImage
import com.example.orgs.ui.modelo.Produtos
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class DetalhesProdutoActivity : AppCompatActivity(R.layout.activity_detalhes_produto) {

    private var produtoId: Long = 0L
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }
    private val produtosDao by lazy {
        AppDatabase.instance(this).produtosDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        procurarIdProduto()
        tentarBuscarProduto()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_produto_remover -> {
                lifecycleScope.launch {
                    produtosDao.buscaId(produtoId).collect {
                        it?.let { produtosDao.remover(it) }
                        finish()
                    }
                }
            }
            R.id.menu_detalhes_produto_editar -> {
                Intent(this@DetalhesProdutoActivity, FormularioProdutoActivity::class.java)
                    .apply {
                        putExtra(CHAVE_PRODUTO_ID, produtoId)
                        startActivity(this)
                    }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun procurarIdProduto() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
    }

    private fun tentarBuscarProduto() {
        lifecycleScope.launch {
            produtosDao.buscaId(produtoId).collect { produtoEncontrado ->
                produtoEncontrado?.let {
                    preencherProdutos(it)
                    title = it.nome
                } ?: finish()
            }
        }
    }

    private fun preencherProdutos(produto: Produtos) {
        with(binding) {
            detalhesProdutoImagemview.tryLoadImage(produto.imagemUrl)
            detalhesProdutoDescricao.text = produto.descricao
            detalhesProdutoTitulo.text = produto.nome
            detalhesProdutoValor.text = produto.valor.formataParaMoedaReal()
        }
    }
}