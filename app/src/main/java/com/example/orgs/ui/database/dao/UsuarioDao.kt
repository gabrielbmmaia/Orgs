package com.example.orgs.ui.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.orgs.ui.modelo.Usuario
import kotlinx.coroutines.flow.Flow

@Dao
interface UsuarioDao {

    @Insert
    suspend fun salva(usuario: Usuario)

    @Query("SELECT * FROM Usuario WHERE id = :usuarioId AND senha = :senha")
    suspend fun autentica(usuarioId: String, senha: String) : Usuario?

    @Query("SELECT * FROM Usuario WHERE id = :usuarioId")
    fun buscaId(usuarioId: String) : Flow<Usuario>
}