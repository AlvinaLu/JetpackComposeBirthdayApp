package com.android.example.jetpackcompose.birthdayapp

import android.annotation.SuppressLint
import android.app.Application
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import com.android.example.jetpackcompose.birthdayapp.ui.theme.AppTheme

/**
 * Created by Alvina Lushnikova on 15.09.22.
 */

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ActionButton(
    isFormValid: Boolean = true,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier.padding(horizontal = 0.dp)
) {

    Button(
        modifier = modifier,
        onClick = onClick,
        enabled = isFormValid,
    ) {
        Row() {
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = null,
                modifier = Modifier.padding(start = 0.dp, end = 12.dp),
                tint = MaterialTheme.colorScheme.primaryContainer
            )

        }
        Text(text = text, color = MaterialTheme.colorScheme.primaryContainer)
    }


}

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun ActionOutlinedButton(
    onClick: () -> Unit,
    text: String
) {
    OutlinedButton(
        onClick = onClick,
    ) {
        Text(text = text)
    }

}


@Preview("light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ActionButtonPreview() {
    AppTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            ActionButton(
                isFormValid =true,
                onClick = {  },
                text = stringResource(id = R.string.save),
            )
        }

    }
}

@Preview("light theme", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Preview("dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ActionOutlinedButtonPreview() {
    AppTheme {
        Surface(modifier = Modifier.background(MaterialTheme.colorScheme.background)) {
            ActionOutlinedButton(onClick = { /*TODO*/ }, text = "DELETE" )
        }

    }
}


