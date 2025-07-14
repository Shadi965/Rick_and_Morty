package ru.work_mate.rickandmorty.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.work_mate.rickandmorty.ui.theme.Orange700

@Composable
fun NetworkStatusIndicator(
    hasNetwork: Boolean,
    hasApiError: Boolean,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        !hasNetwork -> Color.Red.copy(alpha = 0.9f)
        hasApiError -> Orange700.copy(alpha = 0.9f)
        else -> return
    }

    val message =
        if (!hasNetwork) "No internet connection"
        else "API connection error"

    Box(
        modifier = modifier
            .background(
                color = backgroundColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(horizontal = 12.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            color = Color.White,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )
    }
}