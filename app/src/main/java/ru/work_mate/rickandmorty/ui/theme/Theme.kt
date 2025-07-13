package ru.work_mate.rickandmorty.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = RickAndMortyPrimaryDark,
    secondary = RickAndMortySecondaryDark,
    tertiary = RickAndMortyTertiaryDark,
    background = RickAndMortyBackgroundDark,
    surface = RickAndMortySurfaceDark,
    error = RickAndMortyError
)

private val LightColorScheme = lightColorScheme(
    primary = RickAndMortyPrimary,
    secondary = RickAndMortySecondary,
    tertiary = RickAndMortyTertiary,
    background = RickAndMortyBackground,
    surface = RickAndMortySurface,
    error = RickAndMortyError
)

@Composable
fun RickAndMortyTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
