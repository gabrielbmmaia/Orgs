package com.example.orgs.ui.extensions

import android.widget.ImageView
import coil.load
import com.example.orgs.R

fun ImageView.tryLoadImage(url: String?, fallback:Int = R.drawable.imagem_padrao) {
    load(url) {
        fallback(fallback)
        error(R.drawable.imagem_padrao)
        placeholder(R.drawable.placeholder)
    }
}