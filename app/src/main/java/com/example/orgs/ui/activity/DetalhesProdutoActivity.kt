package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.databinding.ActivityDetalhesProdutoBinding
import com.example.orgs.ui.database.AppDatabase
import com.example.orgs.ui.extensions.formataParaMoedaReal
import com.example.orgs.ui.extensions.tryLoadImage
import com.example.orgs.ui.modelo.Produtos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetalhesProdutoActivity : AppCompatActivity(R.layout.activity_detalhes_produto) {

    private var scope = CoroutineScope(IO)
    private var produtoId: Long = 0L
    private var produto: Produtos? = null
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }
    private val produtosDao by lazy {
        AppDatabase.instance(this).produtosDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryLoadProduct()
    }

    override fun onResume() {
        super.onResume()
        buscaProduto()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_detalhes_produto_remover -> {
                scope.launch {
                    produto?.let { produtosDao.remover(it) }
                    finish()
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

    //------------------------------------- funçoes >

    private fun buscaProduto() {
        scope.launch {
            produto = produtosDao.buscaId(produtoId)
            withContext(Main) {
                produto?.let {
                    preencherProdutos(it)
                    title = it.nome
                } ?: finish()
            }
        }
    }

    private fun tryLoadProduct() {
        produtoId = intent.getLongExtra(CHAVE_PRODUTO_ID, 0L)
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