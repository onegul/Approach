package app.approach.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.approach.shared.core.designsystem.component.StatusPill
import app.approach.shared.core.designsystem.theme.ApproachTheme

@Composable
fun App(
    appContainer: AppContainer = AppContainer()
) {
    CompositionLocalProvider(
        LocalAppContainer provides appContainer
    ) {
        ApproachTheme {
            AppContent()
        }
    }
}

@Composable
private fun AppContent() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StatusPill(text = "Local-first")

            Text(
                text = "Approach",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Discover nearby people without internet.",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}