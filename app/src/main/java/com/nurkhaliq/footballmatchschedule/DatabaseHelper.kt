package com.nurkhaliq.footballmatchschedule

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.nurkhaliq.footballmatchschedule.favorite.Favorite
import org.jetbrains.anko.db.*

class DatabaseHelper(ctx: Context) : ManagedSQLiteOpenHelper(ctx, "FavoritesEvent.db", null, 1) {
    companion object {
        private var instance: DatabaseHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): DatabaseHelper {
            if (instance == null) {
                instance = DatabaseHelper(ctx.applicationContext)
            }
            return instance as DatabaseHelper
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            Favorite.TABLE_FAVORITE_TEAM, true,
            Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
            Favorite.ID_EVENT to TEXT + UNIQUE,
            Favorite.ID_HOME to TEXT,
            Favorite.ID_AWAY to TEXT,
            Favorite.DATE_EVENT to TEXT,
            Favorite.STR_HOME_TEAM to TEXT,
            Favorite.STR_AWAY_TEAM to TEXT,
            Favorite.INT_HOME_SCORE to TEXT,
            Favorite.INT_AWAY_SCORE to TEXT
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Favorite.TABLE_FAVORITE_TEAM, true)
    }

}

val Context.database: DatabaseHelper
    get() = DatabaseHelper.getInstance(applicationContext)