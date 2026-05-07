package app.approach.shared

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.approach.shared.core.designsystem.theme.ApproachTheme
import app.approach.shared.feature.home.HomeScreen
import app.approach.shared.feature.profile.ProfileSetupScreen
import kotlinx.coroutines.launch

@Composable
fun App(
    appContainer: AppContainer = remember { AppContainer() }
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
    val appContainer = LocalAppContainer.current
    val coroutineScope = rememberCoroutineScope()

    val profile by appContainer
        .observeProfileUseCase()
        .collectAsStateWithLifecycle(initialValue = null)

    if (profile == null)
        ProfileSetupScreen(
            onSaveProfile = { profile ->
                coroutineScope.launch {
                    appContainer.saveProfileUseCase(profile)
                }
            }
        )
    else
        HomeScreen(profile = profile!!)
}