package ru.netology.nmedia.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.entity.DraftEntity
import ru.netology.nmedia.entity.PostEntity

@Database(entities = [PostEntity::class, DraftEntity::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract val postDao: PostDao

    companion object {
        @Volatile
        private var instance: AppDb? = null

        fun getInstance(context: Context): AppDb {
            return instance ?: synchronized(this) {
                instance ?: buildDataBase(context).also { instance = it }
            }
        }

        private fun buildDataBase(context: Context) = Room.databaseBuilder(context, AppDb::class.java, "app.db")
            .allowMainThreadQueries()
            .build()
    }
}
