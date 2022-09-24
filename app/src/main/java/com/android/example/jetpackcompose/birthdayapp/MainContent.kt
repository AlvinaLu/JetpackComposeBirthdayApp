package com.android.example.jetpackcompose.birthdayapp

import android.app.Application
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.example.jetpackcompose.birthdayapp.model.persons
import com.android.example.jetpackcompose.birthdayapp.ui.theme.AppTheme
import com.android.example.jetpackcompose.birthdayapp.ui.theme.BottomSheetShape

import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.android.example.jetpackcompose.birthdayapp.model.Person

/**
 * Created by Alvina Lushnikova on 06.09.22.
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MainContent(
    allPersons: List<Person>,
    editPersonCard: (Person) -> Unit
){

    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = BottomSheetShape,
            color = MaterialTheme.colorScheme.background,
            shadowElevation = 3.dp,
            tonalElevation = 3.dp
        ) {
            if(allPersons.isNotEmpty()){
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(count = allPersons.size) {
                        PersonCardConstraint(person = allPersons[it], editPersonCard = editPersonCard)
                    }
                }
            }else{
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.background)
                        .padding(vertical = 32.dp),
                    contentAlignment = Alignment.TopCenter
                ) {
                    Text(
                        text = "You haven't birthday persons yet",
                        textAlign = TextAlign.Center,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground,
                    )
                }
            }


        }
    }

}

@Preview("light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainContentPreview() {
    AppTheme {
            MainContent(listOf( Person(
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
                )), editPersonCard = {})
    }
}