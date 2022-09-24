package com.android.example.jetpackcompose.birthdayapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 * Created by Alvina Lushnikova on 07.09.22.
 */
@Entity(tableName = "persons")
data class Person(
    @PrimaryKey(autoGenerate = true)
    @NotNull
    @ColumnInfo(name = "personId")
    val id: Long = 0,

    @ColumnInfo(name = "personName")
    val name: String = "",

    @ColumnInfo(name = "personImage")
    val imageUrl: String = "",

    @ColumnInfo(name = "personDay")
    val day: Int = 15,

    @ColumnInfo(name = "personMonth")
    val month: Int = 6,

    @ColumnInfo(name = "personYear")
    val year: Int = Int.MAX_VALUE,

    @ColumnInfo(name = "oneWeek")
    val oneWeek: Boolean = false,

    @ColumnInfo(name = "dayBefore")
    val dayBefore: Boolean = false,

    @ColumnInfo(name = "onDay")
    val onDay: Boolean = false
)

class PersonTmp(
    var name: String = "",
    var day: Int = 15,
    var month: Int = 6,
    var year: Int = Int.MAX_VALUE,
    var notification: MutableList<Boolean> = mutableListOf<Boolean>(false, false, false),
)

enum class Notification {
    ON_DAY, ON_DAY_BEFORE, ON_WEEK_BEFORE
}


val persons = listOf(
    Person(
        id = 1L,
        name = "Cupcake",
        imageUrl = "https://source.unsplash.com/pGM4sjt_BdQ",
        day = 15
    ),
    Person(
        id = 2L,
        name = "Donut",
        imageUrl = "https://source.unsplash.com/Yc5sL-ejk6U",
        day = 15
    ),
    Person(
        id = 3L,
        name = "Eclair",
        imageUrl = "https://source.unsplash.com/-LojFX9NfPY",
        day = 15
    ),
    Person(
        id = 4L,
        name = "Froyo",
        imageUrl = "https://source.unsplash.com/3U2V5WqK1PQ",
        day = 15
    ),
    Person(
        id = 5L,
        name = "Gingerbread",
        imageUrl = "https://source.unsplash.com/Y4YR9OjdIMk",
        day = 15
    ),
    Person(
        id = 6L,
        name = "Honeycomb",
        imageUrl = "https://source.unsplash.com/bELvIg_KZGU",
        day = 15
    ),
    Person(
        id = 7L,
        name = "Ice Cream Sandwich",
        imageUrl = "https://source.unsplash.com/YgYJsFDd4AU",
        day = 15
    )
)

val links = listOf<String>(
    "https://source.unsplash.com/pGM4sjt_BdQ",
    "https://source.unsplash.com/Yc5sL-ejk6U",
    "https://source.unsplash.com/-LojFX9NfPY",
    "https://source.unsplash.com/3U2V5WqK1PQ",
    "https://source.unsplash.com/Y4YR9OjdIMk",
    "https://source.unsplash.com/bELvIg_KZGU",
    "https://source.unsplash.com/YgYJsFDd4AU",
    "https://source.unsplash.com/0u_vbeOkMpk",
    "https://source.unsplash.com/yb16pT5F_jE",
    "https://source.unsplash.com/AHF_ZktTL6Q",
    "https://source.unsplash.com/rqFm0IgMVYY",
    "https://source.unsplash.com/qRE_OpbVPR8",
    "https://source.unsplash.com/33fWPnyN6tU",
    "https://source.unsplash.com/aX_ljOOyWJY",
    "https://source.unsplash.com/UsSdMZ78Q3E",
    "https://source.unsplash.com/7meCnGCJ5Ms",
    "https://source.unsplash.com/m741tj4Cz7M",
    "https://source.unsplash.com/iuwMdNq0-s4",
    "https://source.unsplash.com/qgWWQU1SzqM",
    "https://source.unsplash.com/9MzCd76xLGk",
    "https://source.unsplash.com/1d9xXWMtQzQ",
    "https://source.unsplash.com/wZxpOw84QTU",
    "https://source.unsplash.com/okzeRxm_GPo",
    "https://source.unsplash.com/l7imGdupuhU",
    "https://source.unsplash.com/bkXzABDt08Q",
    "https://source.unsplash.com/y2MeW00BdBo",
    "https://source.unsplash.com/1oMGgHn-M8k",
    "https://source.unsplash.com/TIGDsyy0TK4"
)

