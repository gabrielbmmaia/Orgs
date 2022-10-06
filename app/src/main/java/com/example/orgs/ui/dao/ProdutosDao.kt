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
        private val produtos = mutableListOf<Produtos>(
            Produtos(
                nome = "Açaí com Banana",
                descricao = "500ml de açai com banana e xarope",
                valor = BigDecimal("21.00"),
                imagemUrl = "https://www.receitasdecomida.com.br/wp-content/uploads/2012/12/acai-na-tigela-com-banana-9543.jpg"
            ),
            Produtos(
                nome = "Açaí com Morango",
                descricao = "500ml de açai com morango e xarope",
                valor = BigDecimal("23.00"),
                imagemUrl = "https://www.auau.com.br/image/cache/data/up_system/product-13756/acai-03-650x650.jpg"
            ),
            Produtos(
                nome = "Açaí com Morango",
                descricao = "500ml de açai com morango e xarope",
                valor = BigDecimal("25.00"),
                imagemUrl = "https://extra.globo.com/incoming/24839343-ad1-7bd/w640h360-PROP/casadinho.jpg"
            ),
            Produtos(
                nome = "Barca de Açaí",
                descricao = "1 litro de açaí com acompanhamentos",
                valor = BigDecimal("45.00"),
                imagemUrl = "https://www.cidadeoferta.com.br/storage/offers/3034-world-acai-barca-imagem1.jpg"
            )
        )
    }
}