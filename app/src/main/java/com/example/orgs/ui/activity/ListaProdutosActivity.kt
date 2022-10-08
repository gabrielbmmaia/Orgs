package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.ui.database.AppDatabase
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

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
    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(produtosDao.mostrarLista())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_lista_produtos,menu)
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
            produtosDao.remover(it)
            adapter.atualiza(produtosDao.mostrarLista())
            Toast.makeText(this, "Produto Deletado", Toast.LENGTH_SHORT).show()
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
                adapter.atualiza(produtosDao.ordenaNomeAsc())
            }
            R.id.menu_lista_produtos_ordenar_nomeDesc -> {
                adapter.atualiza(produtosDao.ordenaNomeDesc())
            }
            R.id.menu_lista_produtos_ordenar_valorAsc -> {
                adapter.atualiza(produtosDao.ordenaValorAsc())
            }
            R.id.menu_lista_produtos_ordenar_valorDesc -> {
                adapter.atualiza(produtosDao.ordenaValorDesc())
            }
            R.id.menu_lista_produtos_ordenar_semOrdem -> {
                adapter.atualiza(produtosDao.semOrdem())
            }
        }
    }
}