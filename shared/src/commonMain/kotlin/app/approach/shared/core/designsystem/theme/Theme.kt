package app.approach.shared.core.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColors = lightColorScheme(
    primary = ApproachBlue,
    onPrimary = ApproachSurfaceLight,
    secondary = ApproachMint,
    onSecondary = ApproachInk,
    error = ApproachCoral,
    background = ApproachBackgroundLight,
    onBackground = ApproachInk,
    surface = ApproachSurfaceLight,
    onSurface = ApproachInk,
    outline = ApproachLine
)

private val DarkColors = darkColorScheme(
    primary = ApproachBlue,
    onPrimary = ApproachSurfaceLight,
    secondary = ApproachMint,
    onSecondary = ApproachInk,
    error = ApproachCoral,
    background = ApproachBackgroundDark,
    onBackground = ApproachBackgroundLight,
    surface = ApproachSurfaceDark,
    onSurface = ApproachBackgroundLight,
    outline = ApproachSlate
)

@Composable
fun ApproachTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    colorScheme: ColorScheme = if (darkTheme) DarkColors else LightColors,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = colorScheme,
        typography = ApproachTypography,
        content = content
    )
}