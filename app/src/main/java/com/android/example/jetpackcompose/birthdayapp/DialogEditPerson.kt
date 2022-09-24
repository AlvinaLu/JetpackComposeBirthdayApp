package com.android.example.jetpackcompose.birthdayapp

import android.app.Application
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.example.jetpackcompose.birthdayapp.model.Person
import com.android.example.jetpackcompose.birthdayapp.ui.theme.AppTheme
import com.android.example.jetpackcompose.birthdayapp.ui.theme.BottomSheetShape
import com.android.example.jetpackcompose.birthdayapp.ui.theme.DragHandleShape
import java.text.DateFormatSymbols
import java.time.LocalDateTime

/**
 * Created by Alvina Lushnikova on 20.09.22.
 */

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun DialogEditPerson(
    state: Person,
    stateValidation: Validation,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    itemChanged: (Int) -> Unit,
    nameChanged: (String) -> Unit,
    dayChanged: (Int) -> Unit,
    monthChanged: (Int) -> Unit,
    yearChanged: (Int) -> Unit,
    savePerson: () -> Unit,
    deletePerson: () -> Unit
) {

    val focusRequester = remember { FocusRequester() }
    val unFocusedColor = MaterialTheme.colorScheme.outline
    val focusedColor = MaterialTheme.colorScheme.tertiary
    var color by remember { mutableStateOf(focusedColor) }

    val year = LocalDateTime.now().year
    val mDays = (1..31).toList()
    val mMonth = (0..11).toList()
    val mYears = (1900..1987).toMutableList()
    mYears.add(Int.MAX_VALUE)
    for (i in 1988..year) {
        mYears.add(i)
    }


    var undefined = stringResource(R.string.date_picker_undefined)





    AppTheme() {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background),
            shape = BottomSheetShape,
            color = MaterialTheme.colorScheme.background

        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = {}) {
                    Surface(
                        modifier = Modifier
                            .absolutePadding(8.dp, 8.dp, 8.dp, 0.dp)
                            .width(50.dp)
                            .height(6.dp)
                            .shadow(1.dp),
                        shape = DragHandleShape, color = MaterialTheme.colorScheme.primary,
                    ) {

                    }
                }
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {

                        Text(
                            text = stringResource(R.string.please_remind_me)
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 20.dp, end = 20.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceAround
                            ) {

                                Reminder(
                                    item = stringResource(id = R.string.one_week),
                                    index = NotificationChip.ONE_WEEK.ordinal,
                                    state = state.oneWeek,
                                    itemChanged = itemChanged
                                )

                                Reminder(
                                    item = stringResource(id = R.string.one_day),
                                    index = NotificationChip.DAY_BEFORE.ordinal,
                                    state = state.dayBefore,
                                    itemChanged = itemChanged
                                )

                                Reminder(
                                    item = stringResource(id = R.string.events_day),
                                    index = NotificationChip.ON_DAY.ordinal,
                                    state = state.onDay,
                                    itemChanged = itemChanged
                                )

                            }

                        }
                        if (stateValidation.validChips == false) {
                            Text(
                                text = stringResource(R.string.error_reminder),
                                color = MaterialTheme.colorScheme.error,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }

                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            StyledField(
                                name = state.name,
                                onValueChange = nameChanged,
                                isValid = stateValidation.validName
                            )
                        }
                        Row(
                            modifier = Modifier
                                .padding(bottom = 24.dp, start = 8.dp, end = 8.dp)
                                .fillMaxWidth()
                                .clickable { focusRequester.requestFocus() }
                                .focusRequester(focusRequester)
                                .onFocusChanged {
                                    color = if (it.isFocused) focusedColor else unFocusedColor
                                }
                                .focusable(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            DatePicker(
                                value = state.day,
                                range = mDays,
                                dividersColor = color,
                                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                                valueChange = dayChanged
                            )
                            DatePicker(
                                value = state.month,
                                label = { DateFormatSymbols().months[it] },
                                range = mMonth,
                                dividersColor = color,
                                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                                valueChange = monthChanged
                            )

                            DatePicker(
                                value = state.year,
                                range = mYears,
                                label = {
                                    if (it == Int.MAX_VALUE) {
                                        return@DatePicker undefined
                                    } else {
                                        it.toString()
                                    }
                                },
                                dividersColor = color,
                                textStyle = TextStyle(color = MaterialTheme.colorScheme.onBackground),
                                valueChange = yearChanged
                            )

                        }
                        if (state.id == -1L) {
                            ActionButton(
                                isFormValid = stateValidation.validChips && stateValidation.validName,
                                onClick = savePerson,
                                text = stringResource(id = R.string.save)
                            )
                        } else {
                            val id = state.id
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 20.dp, end = 20.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                ActionOutlinedButton(
                                    onClick = { },
                                    text = stringResource(R.string.cancel)
                                )
                                ActionOutlinedButton(
                                    onClick = deletePerson,
                                    text = stringResource(R.string.delete)
                                )
                                ActionButton(
                                    isFormValid = stateValidation.validChips && stateValidation.validName,
                                    onClick = savePerson,
                                    text = stringResource(id = R.string.save),
                                )
                            }
                        }
                    }

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview("light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DialogEditPersonPreview() {
    AppTheme {
        val owner = LocalViewModelStoreOwner.current
        owner?.let {
            val viewModel: PersonsViewModel = viewModel(
                it,
                "MainViewModel",
                MainViewModelFactory(
                    LocalContext.current.applicationContext
                            as Application
                )
            )

            val bottomSheetScaffoldState = rememberBottomSheetScaffoldState(
                bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
            )

            DialogEditPerson(
                state = viewModel.state.value,
                stateValidation = viewModel.stateValidation.value,
                bottomSheetScaffoldState = bottomSheetScaffoldState,
                itemChanged = {},
                nameChanged = {},
                dayChanged = {},
                monthChanged = {},
                yearChanged = {},
                deletePerson = {},
                savePerson = {}
            )

        }
    }

}


