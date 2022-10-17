package com.example.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.orgs.databinding.ForumlarioImagemBinding
import com.example.orgs.ui.extensions.tryLoadImage

class FormularioImagemDialog(private val context: Context) {


    fun mostra(urlImagem: String? = null, pegarImagem: (url: String) -> Unit) {
        var binding = ForumlarioImagemBinding
            .inflate(LayoutInflater.from(context))
        var url: String? = null
        urlImagem?.let {
            binding.formularioImagemImageview.tryLoadImage(it)
            binding.url.setText(it)
        }

        binding.formularioImagemBotaoCarregar.setOnClickListener {
            url = binding.url.text.toString()
            binding.formularioImagemImageview.tryLoadImage(url)
        }
        AlertDialog.Builder(context)
            .setView(binding.root)
            .setPositiveButton("Confirmar") { _, _ ->
                url = binding.url.text.toString()
                url?.let { pegarImagem(it) }
            }
            .setNegativeButton("Cancelar") { _, _ ->
            }
            .show()
    }
}