package com.example.orgs.ui.recyclerview.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orgs.databinding.ProdutoItemBinding
import com.example.orgs.ui.extensions.formataParaMoedaReal
import com.example.orgs.ui.extensions.tryLoadImage
import com.example.orgs.ui.modelo.Produtos

class ListaProdutosAdapter(
    private val context: Context,
    produtos: List<Produtos>,
    var onClickItem: (produtos: Produtos) -> Unit = {}
) : RecyclerView.Adapter<ListaProdutosAdapter.ViewHolder>() {

    private val produtos = produtos.toMutableList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ProdutoItemBinding
            .inflate(
                LayoutInflater
                    .from(context),
                parent,
                false
            )
        return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(produtos[position])
    }

    override fun getItemCount(): Int = produtos.size

    @SuppressLint("NotifyDataSetChanged")
    fun atualiza(produtos: List<Produtos>) {
        this.produtos.clear()
        this.produtos.addAll(produtos)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ProdutoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private lateinit var produto: Produtos

        init{
            itemView.setOnClickListener{
                if (::produto.isInitialized){
                   onClickItem(produto)
                }
            }
        }

        private val produtoTitulo = binding.titulo
        private val produtoDescricao = binding.descricao
        private val produtoValor = binding.valor
        private val produtoImagem = binding.imageView

        fun bind(produtos: Produtos) {
            this.produto = produtos
            produtoTitulo.text = produtos.nome
            produtoDescricao.text = produtos.descricao
            produtoValor.text = produtos.valor.formataParaMoedaReal()
            produtoImagem.tryLoadImage(produtos.imagemUrl)

            val visibilidade = if (produtos.imagemUrl != null) {
                View.VISIBLE
            } else {
                View.GONE
            }
            binding.imageView.visibility = visibilidade
        }
    }
}
