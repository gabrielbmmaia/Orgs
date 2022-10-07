package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.example.orgs.R
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.ui.dao.ProdutosDao
import com.example.orgs.ui.database.AppDatabase
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
        criacaoRoomDB()
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
        adapter.onClickItem = {
            val intent = Intent(this@ListaProdutosActivity, DetalhesProdutoActivity::class.java)
                .apply {
                    putExtra(CHAVE_PRODUTO, it)
                }
            startActivity(intent)
        }
    }

    private fun criacaoRoomDB() {
        val db = Room.databaseBuilder(
            this, // aqui, passamos um contexto que pode ser o da própria activity
            AppDatabase::class.java,  //aqui, indicamos a referencia de classe que a gnt cirou de database
            "orgs.db"  //aqui, vai ser o nome do arquivo gerado de banco de dados.
        ).allowMainThreadQueries()
            .build().produtosDao()
    }
}