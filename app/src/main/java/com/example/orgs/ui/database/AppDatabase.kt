package com.example.orgs.ui.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.orgs.ui.database.converter.Converters
import com.example.orgs.ui.database.dao.ProdutosDao
import com.example.orgs.ui.modelo.Produtos


@Database(entities = [Produtos::class], version = 1, exportSchema = true)
@TypeConverters(Converters::class )
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtosDao(): ProdutosDao

    companion object{
        fun instance(context: Context): AppDatabase{
            return Room.databaseBuilder(
                context, // aqui, passamos um contexto que pode ser o da própria activity
                AppDatabase::class.java,  //aqui, indicamos a referencia de classe que a gnt cirou de database
                "orgs.db"  //aqui, vai ser o nome do arquivo gerado de banco de dados.
            ).allowMainThreadQueries()
                .build()
        }
    }
}