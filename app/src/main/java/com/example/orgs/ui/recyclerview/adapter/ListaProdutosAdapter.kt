package com.example.orgs.ui.recyclerview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.R
import com.example.orgs.ui.modelo.Produtos
import kotlinx.android.synthetic.main.produto_item.view.*

class ListaProdutosAdapter(
    //private val context: Context,
    private var produtos: List<Produtos> = ArrayList()
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.produto_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder -> {
                holder.bind(produtos[position])
            }
        }
    }

    override fun getItemCount(): Int = produtos.size

    fun setDataSet(produtos : List<Produtos>){
        this.produtos = produtos
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(produtos: Produtos) {
            val produtoTitulo = itemView.titulo
            produtoTitulo.text = produtos.nome

            val produtoDescricao = itemView.descricao
            produtoDescricao.text = produtos.descricao

            val produtoValor = itemView.valor
            produtoValor.text = produtos.valor
        }
    }
}
