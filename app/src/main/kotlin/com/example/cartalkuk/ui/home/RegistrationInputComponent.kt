@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.cartalkuk.ui.home

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cartalkuk.data.CarTalkConstants.One
import com.example.cartalkuk.ui.home.RegistrationInputComponent.ExampleRegistration
import com.example.cartalkuk.ui.home.RegistrationInputComponent.PreviewValue
import com.example.cartalkuk.ui.home.RegistrationInputComponent.RegistrationLabel
import com.example.cartalkuk.util.toLicensePlateFormat

@Composable
fun RegistrationInputComponent(
    value: String,
    onValueChanged: (queriedReg: String) -> Unit,
    onSearch: () -> Unit,
    focusRequester: FocusRequester
) {
    OutlinedTextField(
        modifier = Modifier.focusRequester(focusRequester),
        value = value,
        maxLines = One,
        singleLine = true,
        label = { RegistrationInputLabel() },
        placeholder = { Text(ExampleRegistration) },
        leadingIcon = @Composable {
            Icon(
                modifier = Modifier.size(36.dp),
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon"
            )
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = { onSearch() }
        ),
        onValueChange = { query ->
            if (query.length <= 8) {
                onValueChanged(query.toLicensePlateFormat())
            }
        }
    )
}

@Composable
fun RegistrationInputLabel() {
    Text(
        text = RegistrationLabel
    )
}

@Preview
@Composable
fun RegistrationInputComponentPreview() {
    RegistrationInputComponent(
        value = PreviewValue,
        onValueChanged = {},
        onSearch = {},
        focusRequester = FocusRequester()
    )
}

private object RegistrationInputComponent {
    const val RegistrationLabel = "Registration"
    const val ExampleRegistration = "CU57ABC"
    const val PreviewValue = "Preview"
}