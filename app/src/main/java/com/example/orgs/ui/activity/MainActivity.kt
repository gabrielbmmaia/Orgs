package com.example.orgs.ui.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.orgs.R
import com.example.orgs.ui.modelo.DataSource
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : Activity() {

    private lateinit var produtosAdapter: ListaProdutosAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()
        addDataSource()
    }

    private fun addDataSource() {
        val dataSource = DataSource.createDataSet()
        this.produtosAdapter.setDataSet(dataSource)
    }

    private fun initRecyclerView() {
        this.produtosAdapter = ListaProdutosAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        recyclerView.adapter = this.produtosAdapter
    }
}