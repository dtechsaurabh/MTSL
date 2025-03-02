//package com.example.mtsl.db
//
//import android.content.Context
//import androidx.room.Database
//import androidx.room.Room
//import androidx.room.RoomDatabase
//abstract class MovieDatabase : RoomDatabase() {
//    abstract fun movieDao(): MovieDao
//
//    companion object {
//        @Volatile
//        private var INSTANCE: MovieDatabase? = null
//
//        fun getDatabase(context: Context): MovieDatabase {
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    MovieDatabase::class.java,
//                    "movie_database"
//                )
//                    .fallbackToDestructiveMigration() // Add this
//                    .build()
//                INSTANCE = instance
//                instance
//            }
//        }
//    }
//}