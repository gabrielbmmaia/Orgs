package com.example.orgs.ui.dialog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import com.example.orgs.databinding.ForumlarioImagemBinding
import com.example.orgs.ui.extensions.tryLoadImage

class FormularioImagemDialog (private val context: Context) {


    fun mostra (pegarImagem: (url: String) -> Unit){
        var binding = ForumlarioImagemBinding.inflate(LayoutInflater.from(context))
        binding.formularioImagemBotaoCarregar.setOnClickListener {
            var url = binding.url.text.toString()
            binding.formularioImagemImageview.tryLoadImage(url)
        }
        AlertDialog.Builder(context)
            .setView(binding.root)
            .setPositiveButton("Confirmar") { _, _ ->
                var url = binding.url.text.toString()
                pegarImagem(url)
            }
            .setNegativeButton("Cancelar") { _, _ ->
            }
            .show()
    }
}