package com.android.example.jetpackcompose.birthdayapp

import android.app.AlarmManager
import android.app.Application
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.os.SystemClock
import androidx.compose.material.BottomSheetScaffoldState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Constraints
import androidx.lifecycle.*
import androidx.work.WorkManager
import com.android.example.jetpackcompose.birthdayapp.data.PersonDatabase
import com.android.example.jetpackcompose.birthdayapp.data.PersonRepository
import com.android.example.jetpackcompose.birthdayapp.model.Person
import com.android.example.jetpackcompose.birthdayapp.model.links
import com.android.example.jetpackcompose.birthdayapp.model.persons
import com.android.example.jetpackcompose.birthdayapp.service.AlarmReceiver
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.random.Random

/**
 * Created by Alvina Lushnikova on 14.09.22.
 */
data class Validation(
    var validName: Boolean = true,
    var validChips: Boolean = true
)

@HiltViewModel
class PersonsViewModel @Inject constructor(application: Application) : ViewModel() {

    val allPersons: LiveData<List<Person>>
    private val repository: PersonRepository
    private val _state = MutableStateFlow(Person())

    val state: StateFlow<Person>
        get() = _state.asStateFlow()

    private val _stateValidation = MutableStateFlow(Validation())

    val stateValidation: StateFlow<Validation> = _stateValidation.asStateFlow()

    @OptIn(ExperimentalMaterialApi::class)
    lateinit var bottomSheetScaffoldState: BottomSheetScaffoldState
    lateinit var coroutineScope: CoroutineScope

    private var alarmMgr: AlarmManager? = null

    private var application: Application? = null



    init {
        val personDb = PersonDatabase.getInstance(application)
        val productDao = personDb.personDao()
        repository = PersonRepository(productDao)
        allPersons = repository.result
        alarmMgr =
            application.applicationContext.getSystemService(Context.ALARM_SERVICE) as? AlarmManager?
        this.application = application


    }


    fun nameValidation() {
        _stateValidation.value.validName =
            _state.value.name.isNotEmpty() && _state.value.name.length > 3
    }

    fun chipsValidation() {
        _stateValidation.value.validChips =
            _state.value.oneWeek || _state.value.dayBefore || _state.value.onDay
    }

    @OptIn(ExperimentalMaterialApi::class)
    fun createNewPersonData(
        bottomSheetScaffoldState: BottomSheetScaffoldState,
        coroutineScope: CoroutineScope
    ) {
        _state.value = Person(
            id = -1L,
            name = "",
            imageUrl = "",
            day = 15,
            month = 6,
            year = Int.MAX_VALUE,
            oneWeek = false,
            dayBefore = false,
            onDay = true
        )
        this.bottomSheetScaffoldState = bottomSheetScaffoldState
        this.coroutineScope = coroutineScope
    }

    @OptIn(ExperimentalMaterialApi::class)
    fun editPersonData(
        person: Person,
        bottomSheetScaffoldState: BottomSheetScaffoldState,
        coroutineScope: CoroutineScope
    ) {
        _state.value = person.copy()
        this.bottomSheetScaffoldState = bottomSheetScaffoldState
        this.coroutineScope = coroutineScope
    }


    fun notificationChanged(index: Int) {
        when (index) {
            NotificationChip.ONE_WEEK.ordinal -> _state.value =
                state.value.copy(oneWeek = !state.value.oneWeek)
            NotificationChip.DAY_BEFORE.ordinal -> _state.value =
                state.value.copy(dayBefore = !state.value.dayBefore)
            NotificationChip.ON_DAY.ordinal -> _state.value =
                state.value.copy(onDay = !state.value.onDay)
        }
        chipsValidation()
    }

    fun dayChanged(day: Int) {
        _state.value = state.value.copy(day = day)

    }

    fun monthChanged(month: Int) {
        _state.value = state.value.copy(month = month)
    }

    fun yearChanged(year: Int) {
        _state.value = state.value.copy(year = year)
    }

    fun nameChanged(name: String) {
        _state.value = state.value.copy(name = name)
        nameValidation()
    }


    @OptIn(ExperimentalMaterialApi::class)
    fun savePerson() {
        nameValidation()
        if (_stateValidation.value.validName && _stateValidation.value.validChips) {
            if (_state.value.id == -1L) {
                insertPerson(
                    Person(
                        name = _state.value.name,
                        imageUrl = links[Random.nextInt(0, links.size - 1)],
                        day = _state.value.day,
                        month = _state.value.month,
                        year = _state.value.year,
                        oneWeek = _state.value.oneWeek,
                        dayBefore = _state.value.dayBefore,
                        onDay = _state.value.onDay
                    )
                )
            } else {
                insertPerson(_state.value)
            }
            coroutineScope.launch {
                withContext(Dispatchers.Main) {
                    if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    fun deletePerson() {
        if (!_state.value.id.equals(-1)) {
            deletePerson(_state.value.id)
            createNewPersonData(bottomSheetScaffoldState, coroutineScope)
            coroutineScope.launch {
                withContext(Dispatchers.Main) {
                    if (bottomSheetScaffoldState.bottomSheetState.isExpanded) {
                        bottomSheetScaffoldState.bottomSheetState.collapse()
                    }
                }
            }
        }
    }


    fun insertPerson(person: Person) {
        repository.insertPerson(person)
        alarm()
    }


    fun deletePerson(id: Long) {
        repository.deletePerson(id)
    }

    fun alarm(){
        var alarmIntent : PendingIntent

        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                repository.result.value?.forEach { person ->
                    alarmIntent = Intent(application,  AlarmReceiver::class.java).let { intent: Intent ->
                        intent.putExtra("TitleExtra", person.name)
                        intent.putExtra("MessageExtra", person.name)
                        PendingIntent.getBroadcast(application, 0, intent, 0)

                    }

                    val calendar: Calendar = Calendar.getInstance().apply {
                        timeInMillis = System.currentTimeMillis()
                        set(Calendar.YEAR, 9, 23, 20, 0)
                    }

                    alarmMgr?.set(
                        AlarmManager.RTC_WAKEUP,
                        calendar.timeInMillis,
                        alarmIntent
                    )
                }
            }
        }


    }

}




class MainViewModelFactory(val application: Application) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PersonsViewModel(application) as T
    }
}

enum class NotificationChip {
    ONE_WEEK,
    DAY_BEFORE,
    ON_DAY
}

