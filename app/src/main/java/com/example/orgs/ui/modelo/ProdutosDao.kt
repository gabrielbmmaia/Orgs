package com.example.orgs.ui.modelo

import java.math.BigDecimal

class ProdutosDao {

    fun adiciona(produto: Produtos) {
        produtos.add(produto)
    }

    fun mostrarLista(): List<Produtos> {
        return produtos.toList()
    }

    companion object {
        private val produtos = mutableListOf<Produtos>(Produtos(
                nome = "Salada de Frutas",
                descricao = "Abacaxi, Banana, Mamão",
                valor = BigDecimal("17.88")
            )
        )
    }
}