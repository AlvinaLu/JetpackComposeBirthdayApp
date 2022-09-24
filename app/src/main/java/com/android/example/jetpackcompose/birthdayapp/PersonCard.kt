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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.android.example.jetpackcompose.birthdayapp.model.Person
import com.android.example.jetpackcompose.birthdayapp.ui.theme.AppTheme
import com.android.example.jetpackcompose.birthdayapp.ui.theme.CardShape


/**
 * Created by Alvina Lushnikova on 07.09.22.
 */
@Composable
fun PersonCard(
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
            Row(
                modifier = Modifier
                    .clickable(onClick = { })
            ) {
                Box(
                    modifier = Modifier
                        .height(120.dp)
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .fillMaxWidth()
                ) {
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
                    )
                    PersonImage(
                        imageUrl = person.imageUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .padding(16.dp)
                            .align(Alignment.TopStart)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .height(60.dp)
                            .width(240.dp)
                            .align(Alignment.TopEnd)
                            .background(
                                Color.Transparent
                            )
                    ) {
                        Text(
                            text = person.name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                        )
                        IconButton(modifier = Modifier.align(Alignment.TopEnd), onClick = { editPersonCard(person)}) {
                            Icon(
                                imageVector = Icons.Filled.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.background
                            )
                        }
                    }
                    Box(
                        modifier = Modifier
                            .height(60.dp)
                            .width(240.dp)
                            .align(Alignment.BottomEnd)
                            .background(
                                Color.Transparent
                            )
                    ) {
                        Text(
                            text = "${person.day}  ${person.month}  ${ if (person.year == Int.MAX_VALUE) {
                                "undefined"
                            } else {
                                person.year
                            }}",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier
                                .padding(vertical = 16.dp).align(Alignment.CenterStart)
                        )
                        Text(
                            text = "${person.oneWeek}  ${person.onDay}  ${person.dayBefore}",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier
                                .padding(vertical = 16.dp).align(Alignment.Center)
                        )
                    }

                }


            }
        }
    }
}


@Composable
fun PersonImage(
    imageUrl: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    elevation: Dp = 1.dp
) {
    Surface(
        color = Color.LightGray,
        shape = CircleShape,
        modifier = modifier
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl)
                .crossfade(true)
                .build(),
            contentDescription = contentDescription,
            placeholder = painterResource(R.drawable.placeholder),
            modifier = Modifier
                .width(50.dp)
                .height(50.dp)
                .shadow(1.dp),
            contentScale = ContentScale.Crop,
        )
    }
}


@Preview("light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PersonCardPreview() {
    AppTheme {
        PersonCard(
            Person(
                id = 1L,
                name = "Cupcake",
                imageUrl = "https://source.unsplash.com/pGM4sjt_BdQ",
                day = 12
            ), editPersonCard = {}
        )
    }
}