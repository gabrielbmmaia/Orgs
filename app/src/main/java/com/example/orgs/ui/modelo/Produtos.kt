package com.example.orgs.ui.modelo


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Parcelize
data class Produtos(
    val nome: String,
    val descricao: String,
    val valor: BigDecimal,
    val imagemUrl: String? = null
): Parcelable