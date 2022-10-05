package com.example.orgs.ui.dao

import com.example.orgs.ui.modelo.Produtos
import java.math.BigDecimal

class ProdutosDao {

    fun adiciona(produto: Produtos) {
        produtos.add(produto)
    }

    fun mostrarLista(): List<Produtos> {
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produtos>()
    }
}