package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.databinding.ActivityFormularioProdutoBinding
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.ui.dao.ProdutosDao
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter

class ListaProdutosActivity : AppCompatActivity(R.layout.activity_lista_produtos) {


    private val dao = ProdutosDao()
    private val adapter = ListaProdutosAdapter(this, dao.mostrarLista())
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
        val bindingFormularioProduto = ActivityFormularioProdutoBinding.inflate(layoutInflater)


    }

    override fun onResume() {
        super.onResume()
        adapter.atualiza(dao.mostrarLista())
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
    }
}