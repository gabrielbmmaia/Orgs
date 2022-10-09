package com.example.orgs.ui.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.orgs.ui.modelo.Produtos

@Dao
interface ProdutosDao {

    @Query("SELECT * FROM Produtos")
    fun mostrarLista(): List<Produtos>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun adiciona(vararg produtos: Produtos)

    @Delete
    fun remover (vararg produtos: Produtos)

    @Query("SELECT*FROM Produtos WHERE id = :id")
    fun buscaId (vararg id: Long) : Produtos?

    @Query("SELECT * FROM Produtos ORDER BY nome ASC")
    fun ordenaNomeAsc() : List<Produtos>

    @Query("SELECT * FROM Produtos ORDER BY nome DESC")
    fun ordenaNomeDesc() : List<Produtos>

    @Query("SELECT * FROM Produtos ORDER BY valor ASC")
    fun ordenaValorAsc() : List<Produtos>

    @Query("SELECT * FROM Produtos ORDER BY valor DESC")
    fun ordenaValorDesc() : List<Produtos>
}