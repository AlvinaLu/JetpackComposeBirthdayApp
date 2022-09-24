package com.android.example.jetpackcompose.birthdayapp

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.layoutId
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.android.example.jetpackcompose.birthdayapp.model.Person
import com.android.example.jetpackcompose.birthdayapp.ui.theme.AppTheme
import com.android.example.jetpackcompose.birthdayapp.ui.theme.CardShape
import com.android.example.jetpackcompose.birthdayapp.utils.cut
import java.text.DateFormatSymbols
import java.time.LocalDateTime

/**
 * Created by Alvina Lushnikova on 21.09.22.
 */

@Composable
fun PersonCardConstraint(
    person: Person,
    editPersonCard: (Person) -> Unit
) {


    AppTheme {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, start = 8.dp, end = 8.dp)
                .background(Color.Transparent),
            shape = CardShape,
            elevation = 3.dp
        ) {

            ConstraintLayout(modifier = Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.secondaryContainer)) {
                val (
                    topBox, bottomBox, personImage, contentContainer, textName, editButton, dataText, turnsText, yearText
                ) = createRefs()
                Box(
                    modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color(0xFF6750A4),
                                    Color(0xFFF2B8B5),
                                    Color(0xFF6750A4),
                                )
                            )
                        )
                        .constrainAs(topBox) {
                            start.linkTo(parent.start, 0.dp)
                        }
                )
                PersonImage(
                    imageUrl = person.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(16.dp)
                        .constrainAs(personImage) {
                            start.linkTo(parent.start, 8.dp)
                            top.linkTo(parent.top)
                        }
                )

                Text(
                    modifier = Modifier.constrainAs(textName) {
                        start.linkTo(personImage.end, 8.dp)
                        top.linkTo(topBox.top, 8.dp)
                        bottom.linkTo(topBox.bottom, 8.dp)
                    },
                    text = person.name.cut(12),
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                )

                IconButton(modifier = Modifier.constrainAs(editButton) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }, onClick = { editPersonCard(person) }) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }

                Text(
                    text = "${person.day}  ${DateFormatSymbols().months[person.month]} " +
                            " ${ if (person.year != Int.MAX_VALUE) {
                                person.year.toString()
                    } else {
                         "undefined"
                    }}",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    modifier = Modifier.constrainAs(dataText){
                        start.linkTo(personImage.end, 16.dp)
                        bottom.linkTo(parent.bottom, 8.dp)
                        top.linkTo(topBox.bottom, 8.dp)
                    }
                )

                if(person.year != Int.MAX_VALUE){
                    Text(
                        text = "turns",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.constrainAs(turnsText){
                            end.linkTo(parent.end, 16.dp)
                            top.linkTo(topBox.bottom, 8.dp)
                        }
                    )

                    Text(
                        text = "${LocalDateTime.now().year - person.year}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.constrainAs(yearText){
                            end.linkTo(parent.end, 16.dp)
                            top.linkTo(turnsText.bottom, 0.dp)
                            bottom.linkTo(parent.bottom, 8.dp)
                        }
                    )
                }


            }
        }
    }

}

@Preview("light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PersonCardConstraintPreview() {
    AppTheme {
        PersonCardConstraint(
            Person(
                id = 1L,
                name = "Cupcakev fdgfdgdf dfddg",
                imageUrl = "https://source.unsplash.com/pGM4sjt_BdQ",
                day = 12,
                month = 11,
                year = 2012
            ), editPersonCard = {}
        )
    }
}