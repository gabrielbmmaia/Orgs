package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.appcompat.widget.PopupMenu.OnMenuItemClickListener
import androidx.core.view.get
import com.example.orgs.R
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.ui.database.AppDatabase
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

class ListaProdutosActivity : AppCompatActivity(R.layout.activity_lista_produtos){

    private val adapter = ListaProdutosAdapter(this)
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        val db = AppDatabase.instance(this)
        val produtosDao = db.produtosDao()
        adapter.atualiza(produtosDao.mostrarLista())
    }

    private fun configuraFab() {
        val fab = binding.extendedFab
        fab.setOnClickListener {
            val intent = Intent(this@ListaProdutosActivity, FormularioProdutoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        adapter.onClickItem = {
            val intent = Intent(this@ListaProdutosActivity, DetalhesProdutoActivity::class.java)
                .apply {
                    putExtra(CHAVE_PRODUTO, it)
                }
            startActivity(intent)
        }
        adapter.quandoClicaEmRemover={
            Log.i("MenuPopup", "onMenuItemClick: Remover")
        }
        adapter.quandoClicaEmEditar = {
            Log.i("MenuPopup", "onMenuItemClick: Editar")
        }
    }
}