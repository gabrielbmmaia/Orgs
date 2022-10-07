package com.example.orgs.ui.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.orgs.ui.database.converter.Converters
import com.example.orgs.ui.database.dao.ProdutosDao
import com.example.orgs.ui.modelo.Produtos


@Database(entities = [Produtos::class], version = 1)
@TypeConverters(Converters::class )
abstract class AppDatabase : RoomDatabase() {
    abstract fun produtosDao(): ProdutosDao
}