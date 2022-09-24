package com.android.example.jetpackcompose.birthdayapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.android.example.jetpackcompose.birthdayapp.model.Person

/**
 * Created by Alvina Lushnikova on 14.09.22.
 */
@Dao
interface PersonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPerson(person: Person)

    @Query("SELECT * FROM persons WHERE personId = :id")
    fun findPerson(id: Long) : Person

    @Query("SELECT * FROM persons")
    fun getAllPersons(): LiveData<List<Person>>

    @Query("DELETE FROM persons WHERE personId = :id")
    fun deletePerson(id: Long)
}