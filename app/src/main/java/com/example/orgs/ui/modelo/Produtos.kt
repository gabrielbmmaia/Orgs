package com.example.orgs.ui.modelo


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.math.BigDecimal

@Entity
@Parcelize
data class Produtos(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val nome: String,
    val descricao: String,
    val valor: BigDecimal,
    val imagemUrl: String? = null,
    val usuarioId: String? = null // só é null porque produtos antigos não terá esse campo registrado, logo, será nulo
): Parcelable