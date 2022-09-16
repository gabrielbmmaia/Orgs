package com.example.orgs.ui.modelo

class DataSource {

    companion object {
        fun createDataSet(): ArrayList<Produtos> {
            val list = ArrayList<Produtos>()

            list.add(
                Produtos(
                    nome = "Cesta de Frutas",
                    descricao = "Cesta de frutas contendo: maçã, pêra, melancia e abacaxi",
                    valor = "19.99"
                )
            )
            list.add(
                Produtos(
                    nome = "Suco de Uva DelValle",
                    descricao = "Suco sabor Uva, 500ml",
                    valor = "7.99"
                )
            )
            list.add(
                Produtos(
                    nome = "Frango Seara",
                    descricao = "1 kg de peito de frango da Serasa",
                    valor = "24.50"
                )
            )
            list.add(
                Produtos(
                    nome = "Batata Frita Sadia",
                    descricao = "1 kg de batata frita da Sadia",
                    valor = "14.99"
                )
            )
            list.add(
                Produtos(
                    nome = "Arroz Parbolizado",
                    descricao = "1 kg de arroz parbolizado da Camil",
                    valor = "8.99"
                )
            )
            return list
        }
    }
}