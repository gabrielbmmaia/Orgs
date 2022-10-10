package com.example.orgs.ui.database.dao

import androidx.room.*
import com.example.orgs.ui.modelo.Produtos
import kotlinx.coroutines.flow.Flow

@Dao
interface ProdutosDao {

    @Query("SELECT * FROM Produtos")
    fun mostrarLista(): Flow<List<Produtos>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)  //(onConflict = OnConflictStrategy.REPLACE)
    suspend fun adiciona(vararg produtos: Produtos)

    @Delete
    suspend fun remover (vararg produtos: Produtos)

    @Query("SELECT*FROM Produtos WHERE id = :id")
    fun buscaId (vararg id: Long) : Flow<Produtos?>

    @Query("SELECT * FROM Produtos ORDER BY nome ASC")
    fun ordenaNomeAsc() : Flow<List<Produtos>>

    @Query("SELECT * FROM Produtos ORDER BY nome DESC")
    fun ordenaNomeDesc() : Flow<List<Produtos>>

    @Query("SELECT * FROM Produtos ORDER BY valor ASC")
    fun ordenaValorAsc() : Flow<List<Produtos>>

    @Query("SELECT * FROM Produtos ORDER BY valor DESC")
    fun ordenaValorDesc() : Flow<List<Produtos>>
}