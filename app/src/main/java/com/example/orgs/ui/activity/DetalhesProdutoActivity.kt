package com.example.orgs.ui.activity

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.orgs.R
import com.example.orgs.databinding.ActivityDetalhesProdutoBinding
import com.example.orgs.ui.extensions.formataParaMoedaReal
import com.example.orgs.ui.extensions.tryLoadImage
import com.example.orgs.ui.modelo.Produtos

class DetalhesProdutoActivity : AppCompatActivity(R.layout.activity_detalhes_produto){

    private val binding by lazy {
        ActivityDetalhesProdutoBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        tryLoadProduct()
    }

    private fun tryLoadProduct(){
        intent.getParcelableExtra<Produtos>(CHAVE_PRODUTO)?.let { produtoCarregado ->
            preencherProdutos(produtoCarregado)
        } ?: finish()
    }

    private fun preencherProdutos(produtoCarregado: Produtos) {
        with(binding){
            detalhesProdutoImagemview.tryLoadImage(produtoCarregado.imagemUrl)
            detalhesProdutoDescricao.text = produtoCarregado.descricao
            detalhesProdutoTitulo.text = produtoCarregado.nome
            detalhesProdutoValor.text = produtoCarregado.valor.formataParaMoedaReal()
        }
    }
}