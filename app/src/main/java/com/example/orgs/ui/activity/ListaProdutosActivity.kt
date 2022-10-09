package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.ui.database.AppDatabase
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class ListaProdutosActivity : AppCompatActivity(R.layout.activity_lista_produtos) {

    private val scope = CoroutineScope(IO)
    private val adapter = ListaProdutosAdapter(this)
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }
    private val produtosDao by lazy {
        AppDatabase.instance(this).produtosDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        scope.launch {
            val produtos = produtosDao.mostrarLista()
            withContext(Main) {
                adapter.atualiza(produtos)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        configuraMenuOrdenacao(item)
        return super.onOptionsItemSelected(item)
    }

    private fun configuraFab() {
        val fab = binding.extendedFab
        fab.setOnClickListener {
            Intent(this@ListaProdutosActivity, FormularioProdutoActivity::class.java)
                .apply {
                    startActivity(this)
                }
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        adapter.onClickItem = {
            Intent(this@ListaProdutosActivity, DetalhesProdutoActivity::class.java)
                .apply {
                    putExtra(CHAVE_PRODUTO_ID, it.id)
                    startActivity(this)
                }
        }
        adapter.quandoClicaEmRemover = {
            scope.launch {
                produtosDao.remover(it)
                val produto = withContext(IO) {
                    produtosDao.mostrarLista()
                }
                withContext(Main) {
                    adapter.atualiza(produto)
                    Toast.makeText(
                        this@ListaProdutosActivity,
                        "Produto Deletado",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        adapter.quandoClicaEmEditar =
            {
                Intent(this@ListaProdutosActivity, FormularioProdutoActivity::class.java)
                    .apply {
                        putExtra(CHAVE_PRODUTO_ID, it.id)
                        startActivity(this)
                    }
            }
    }

    private fun configuraMenuOrdenacao(item: MenuItem) {
        when (item.itemId) {
            R.id.menu_lista_produtos_ordenar_nomeAsc -> {
                scope.launch {
                    val produto = produtosDao.ordenaNomeAsc()
                    withContext(Main) {
                        adapter.atualiza(produto)
                    }
                }
            }
            R.id.menu_lista_produtos_ordenar_nomeDesc -> {
                scope.launch {
                    val produto = produtosDao.ordenaNomeDesc()
                    withContext(Main) {
                        adapter.atualiza(produto)
                    }
                }
            }
            R.id.menu_lista_produtos_ordenar_valorAsc -> {
                scope.launch {
                    val produto = produtosDao.ordenaValorAsc()
                    withContext(Main) {
                        adapter.atualiza(produto)
                    }
                }
            }
            R.id.menu_lista_produtos_ordenar_valorDesc -> {
                scope.launch {
                    val produto = produtosDao.ordenaValorDesc()
                    withContext(Main) {
                        adapter.atualiza(produto)
                    }
                }
            }
            R.id.menu_lista_produtos_ordenar_semOrdem -> {
                scope.launch {
                    val produto = produtosDao.mostrarLista()
                    withContext(Main) {
                        adapter.atualiza(produto)
                    }
                }
            }
        }
    }
}