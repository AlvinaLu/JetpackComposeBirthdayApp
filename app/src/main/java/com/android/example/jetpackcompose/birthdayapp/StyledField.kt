package com.android.example.jetpackcompose.birthdayapp

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.example.jetpackcompose.birthdayapp.ui.theme.AppTheme

/**
 * Created by Alvina Lushnikova on 08.09.22.
 */
@Composable
fun StyledField(
    name: String,
    onValueChange: (String) -> Unit,
    isValid: Boolean
) {

    AppTheme {
        OutlinedTextField(
            value = name,
            onValueChange = onValueChange,
            label = {
                if (!isValid) {
                    Text(
                        text = stringResource(R.string.name_error)
                    )
                } else {
                    Text(
                        text = stringResource(R.string.enter_name)
                    )
                }

            },
            isError = !isValid,
            maxLines = 1,
            textStyle = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp, start = 24.dp, end = 24.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colorScheme.onBackground,
                focusedBorderColor = MaterialTheme.colorScheme.tertiary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                unfocusedLabelColor = MaterialTheme.colorScheme.outline,
                focusedLabelColor = MaterialTheme.colorScheme.tertiary,
                cursorColor = MaterialTheme.colorScheme.tertiary,
                disabledTextColor = MaterialTheme.colorScheme.outline,
                placeholderColor = MaterialTheme.colorScheme.outline
            )
        )
    }

}

@Preview("light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun StyledFieldPreview() {
    AppTheme {
        //StyledField()
    }
}