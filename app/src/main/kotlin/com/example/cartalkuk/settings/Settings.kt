package com.example.cartalkuk.settings

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.cartalkuk.settings.AppSettings.colourTheme
import com.example.cartalkuk.settings.ColourTheme.Light
import com.example.cartalkuk.settings.ColourTheme.Dark
import com.example.cartalkuk.settings.ColourTheme.System
import com.example.cartalkuk.settings.ColourTheme.valueOf
import com.example.cartalkuk.settings.Settings.ColourTheme

object AppSettings {
    var colourTheme by mutableStateOf(System)
}

enum class ColourTheme {
    Light,
    Dark,
    System
}

@Composable
fun ThemeModeSelection() {
    val context = LocalContext.current
    Column {
        ColourThemeOption(
            text = Light.name,
            onClick = {
                colourTheme = Light
                context.setColourThemeFromData(Light)
            }
        )
        ColourThemeOption(
            text = Dark.name,
            onClick = {
                colourTheme = Dark
                context.setColourThemeFromData(Dark)
            }
        )
        ColourThemeOption(
            text = System.name,
            onClick = {
                colourTheme = System
                context.setColourThemeFromData(System)
            }
        )
    }
}

@Composable
fun ColourThemeOption(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Text(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onClick()
            }
            .padding(horizontal = 16.dp),
        text = text,
        style = MaterialTheme.typography.titleSmall,
    )
}

fun Context.getColourThemeFromData(): ColourTheme {
    val colourThemePref = this.getSharedPreferences(
        ColourTheme,
        Context.MODE_PRIVATE
    )

    return colourThemePref?.getString(ColourTheme, System.name)?.let { savedTheme ->
        valueOf(savedTheme)
    } ?: System
}

fun Context.setColourThemeFromData(theme: ColourTheme) {
    val colourThemePref = this.getSharedPreferences(
        ColourTheme,
        Context.MODE_PRIVATE
    )
    with(colourThemePref.edit()) {
        putString(ColourTheme, theme.name)
        apply()
    }
}

internal object Settings {
    const val ColourTheme = "ColourTheme"
}