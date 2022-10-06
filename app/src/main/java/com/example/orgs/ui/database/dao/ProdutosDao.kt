package com.example.orgs.ui.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.orgs.ui.modelo.Produtos

@Dao
interface ProdutosDao {

    @Query("SELECT * FROM Produtos")
    fun mostrarLista(): List<Produtos>

    @Insert
    fun adiciona(produtos: Produtos)

}