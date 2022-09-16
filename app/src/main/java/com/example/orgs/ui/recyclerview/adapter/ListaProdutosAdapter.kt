package com.example.orgs.ui.recyclerview.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.ui.modelo.Produtos
import kotlinx.android.synthetic.main.produto_item.view.*

class ListaProdutosAdapter(
    private val produtos: List<Produtos> = ArrayList()
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(view = view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int = produtos.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val produtoTitulo = itemView.titulo
        private val produtoDescricao = itemView.descricao
        private val produtoValor = itemView.valor

        fun bind(produtos : Produtos) {
             produtoTitulo.text = produtos.nome
             produtoDescricao.text = produtos.descricao
             produtoValor.text = produtos.valor.toString()
        }

    }


}
