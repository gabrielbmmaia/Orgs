package com.example.orgs.ui.modelo

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Usuario(
    @PrimaryKey
    val id: String,
    val nome: String,
    val senha: String
)

