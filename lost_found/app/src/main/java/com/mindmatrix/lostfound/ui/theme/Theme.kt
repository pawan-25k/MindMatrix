package com.mindmatrix.lostfound.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// ── Brand colours ────────────────────────────────────────────────────────────
val Brand600    = Color(0xFF1565C0)  // Deep blue – primary
val Brand400    = Color(0xFF42A5F5)  // Sky blue – secondary / accent
val Brand50     = Color(0xFFE3F2FD)  // Very light blue – background tint
val Teal500     = Color(0xFF009688)  // Teal – tertiary / found-item indicator
val ErrorRed    = Color(0xFFD32F2F)
val SurfaceDark = Color(0xFF1A1D2E)  // Navy dark surface
val OnSurface   = Color(0xFFE8EAF6)
val GoldAccent  = Color(0xFFFFC107)  // Lost-item badge accent

// ── Light colour scheme ──────────────────────────────────────────────────────
private val LightColors = lightColorScheme(
    primary          = Brand600,
    onPrimary        = Color.White,
    primaryContainer = Brand50,
    onPrimaryContainer = Brand600,
    secondary        = Teal500,
    onSecondary      = Color.White,
    tertiary         = GoldAccent,
    onTertiary       = Color(0xFF1A1A1A),
    background       = Color(0xFFF5F7FA),
    onBackground     = Color(0xFF1A1A2E),
    surface          = Color.White,
    onSurface        = Color(0xFF1A1A2E),
    surfaceVariant   = Brand50,
    error            = ErrorRed,
    onError          = Color.White
)

// ── Dark colour scheme ───────────────────────────────────────────────────────
private val DarkColors = darkColorScheme(
    primary          = Brand400,
    onPrimary        = Color(0xFF0D1B4B),
    primaryContainer = Color(0xFF1A237E),
    onPrimaryContainer = Brand50,
    secondary        = Color(0xFF80CBC4),
    onSecondary      = Color(0xFF00251A),
    tertiary         = GoldAccent,
    onTertiary       = Color(0xFF1A1A1A),
    background       = Color(0xFF0D1117),
    onBackground     = OnSurface,
    surface          = SurfaceDark,
    onSurface        = OnSurface,
    surfaceVariant   = Color(0xFF1E2740),
    error            = Color(0xFFEF9A9A),
    onError          = Color(0xFF601410)
)

/**
 * LostFoundTheme – wraps the app content with the Material 3 theme.
 *
 * Pass [darkTheme] = true to force dark mode (useful for previews).
 * The default uses the system setting.
 */
@Composable
fun LostFoundTheme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        content     = content
    )
}
