package com.android.example.jetpackcompose.birthdayapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.example.jetpackcompose.birthdayapp.model.Person

/**
 * Created by Alvina Lushnikova on 14.09.22.
 */


@Database(entities = [(Person::class)], version = 2, exportSchema = false)
abstract class PersonDatabase: RoomDatabase() {

    abstract fun personDao(): PersonDao

    companion object {

        private var INSTANCE: PersonDatabase? = null

        fun getInstance(context: Context): PersonDatabase {
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PersonDatabase::class.java,
                        "person_database"
                    ).fallbackToDestructiveMigration()
                        .build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}