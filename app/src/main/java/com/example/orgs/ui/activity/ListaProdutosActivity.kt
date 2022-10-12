package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.orgs.R
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.ui.database.AppDatabase
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class ListaProdutosActivity : AppCompatActivity(R.layout.activity_lista_produtos) {

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
        atualizaTela()
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
            lifecycleScope.launch {
                produtosDao.remover(it)
                Toast.makeText(
                    this@ListaProdutosActivity,
                    "Produto Deletado",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        adapter.quandoClicaEmEditar = {
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
                lifecycleScope.launch {
                    produtosDao.ordenaNomeAsc().collect() {
                        adapter.atualiza(it)
                    }
                }
            }
            R.id.menu_lista_produtos_ordenar_nomeDesc -> {
                lifecycleScope.launch {
                    produtosDao.ordenaNomeDesc().collect() {
                        adapter.atualiza(it)
                    }
                }
            }
            R.id.menu_lista_produtos_ordenar_valorAsc -> {
                lifecycleScope.launch {
                    produtosDao.ordenaValorAsc().collect() {
                        adapter.atualiza(it)
                    }
                }
            }
            R.id.menu_lista_produtos_ordenar_valorDesc -> {
                lifecycleScope.launch {
                    produtosDao.ordenaValorDesc().collect() {
                        adapter.atualiza(it)
                    }
                }
            }
            R.id.menu_lista_produtos_ordenar_semOrdem -> {
                atualizaTela()
            }
        }
    }

    private fun atualizaTela() {
        lifecycleScope.launch {
            produtosDao.mostrarLista().collect() {
                adapter.atualiza(it)
            }
        }
    }
}