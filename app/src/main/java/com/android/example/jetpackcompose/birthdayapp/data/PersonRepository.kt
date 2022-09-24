package com.android.example.jetpackcompose.birthdayapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.android.example.jetpackcompose.birthdayapp.model.Person
import kotlinx.coroutines.*

/**
 * Created by Alvina Lushnikova on 14.09.22.
 */
class PersonRepository(private val personDao: PersonDao) {

    val result : LiveData<List<Person>> = personDao.getAllPersons()

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertPerson(newPerson: Person){
        coroutineScope.launch(Dispatchers.IO) {
            personDao.insertPerson(newPerson)
        }
    }

    fun changePerson(newPerson: Person){
        coroutineScope.launch(Dispatchers.IO) {
            personDao.insertPerson(newPerson)
        }
    }

    fun deletePerson(id: Long) {
        coroutineScope.launch(Dispatchers.IO) {
            personDao.deletePerson(id)
        }
    }

    fun findPerson(id: Long) {
        coroutineScope.launch(Dispatchers.Main) {
            val tmp = asyncFind(id).await()
            if(tmp!=null){
            }

        }
    }

    private fun asyncFind(id: Long): Deferred<Person?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async personDao.findPerson(id)
        }

    fun getAllPersons(){
        coroutineScope.launch(Dispatchers.IO){
            personDao.getAllPersons()
        }
    }
}
