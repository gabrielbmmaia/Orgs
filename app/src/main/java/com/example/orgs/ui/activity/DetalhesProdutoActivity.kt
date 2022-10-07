package com.example.orgs.ui.activity

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

class DetalhesProdutoActivity : AppCompatActivity(R.layout.activity_detalhes_produto) {

    private lateinit var produtos: Produtos
    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryLoadProduct()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_detalhes_produto, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (::produtos.isInitialized) {
            val db = AppDatabase.instance(this)
            val produtoDao = db.produtosDao()
            when (item.itemId) {
                R.id.menu_detalhes_produto_remover -> {
                    produtoDao.remover(produtos)
                    finish()
                }
                R.id.menu_detalhes_produto_editar -> {

                }
            }
        }
            return super.onOptionsItemSelected(item)
    }

    private fun tryLoadProduct() {
        intent.getParcelableExtra<Produtos>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            produtos = produtoCarregado
            preencherProdutos(produtoCarregado)
        } ?: finish()
    }

    private fun preencherProdutos(produtoCarregado: Produtos) {
        with(binding) {
            detalhesProdutoImagemview.tryLoadImage(produtoCarregado.imagemUrl)
            detalhesProdutoDescricao.text = produtoCarregado.descricao
            detalhesProdutoTitulo.text = produtoCarregado.nome
            detalhesProdutoValor.text = produtoCarregado.valor.formataParaMoedaReal()
        }
    }
}