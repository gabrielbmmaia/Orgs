package com.example.orgs.ui.modelo

class ProdutosDao {

    private val produtos = mutableListOf<Produtos>()

    fun adiciona(produto: Produtos) {
        produtos.add(produto)
    }

    fun mostrarLista(): List<Produtos> {
        return produtos.toList()
    }
}