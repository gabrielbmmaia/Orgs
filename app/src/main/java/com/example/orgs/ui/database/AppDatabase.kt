package com.example.orgs.ui.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.orgs.ui.database.converter.Converters
import com.example.orgs.ui.database.dao.ProdutosDao
import com.example.orgs.ui.database.dao.UsuarioDao
import com.example.orgs.ui.modelo.Produtos
import com.example.orgs.ui.modelo.Usuario


@Database(
    entities = [Produtos::class, Usuario::class],
    version = 3,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtosDao(): ProdutosDao
    abstract fun usuarioDao(): UsuarioDao

    companion object {

        @Volatile // serve para caso dois threads acessem o db ao mesmo tempo quando o primeiro cria a instancia do db, o outro já consegue ler a mesma instancia
        private var db: AppDatabase? = null // criação de um singleton da instancia do DB

        fun instance(context: Context): AppDatabase {
            return db ?: Room.databaseBuilder( // aqui checamos se o db já foi instancializado
                context, // aqui, passamos um contexto que pode ser o da própria activity
                AppDatabase::class.java,  //aqui, indicamos a referencia de classe que a gnt cirou de database
                "orgs.db"  //aqui, vai ser o nome do arquivo gerado de banco de dados.
            ).addMigrations(
                MIGRATION_1_2,
                MIGRATION_2_3
            )
                .build().also {
                    db = it // essa parte é necessaria para instancialização do singleton
                }
        }
    }
}