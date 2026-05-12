package app.approach.shared

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.approach.shared.core.designsystem.theme.ApproachTheme
import app.approach.shared.feature.home.HomeScreen
import app.approach.shared.feature.profile.ProfileEditScreen
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

    val nearbyPeers by appContainer
        .observeNearbyPeersUseCase()
        .collectAsStateWithLifecycle(initialValue = emptyList())

    val isBroadcasting by appContainer
        .observeBroadcastingStateUseCase()
        .collectAsStateWithLifecycle(initialValue = false)

    var currentScreen by remember { mutableStateOf(AppScreen.Home) }

    val currentProfile = profile

    if (currentProfile == null)
        ProfileSetupScreen(
            onSaveProfile = { profile ->
                coroutineScope.launch {
                    appContainer.saveProfileUseCase(profile)
                }
            }
        )
    else
        when (currentScreen) {
            AppScreen.Home -> HomeScreen(
                profile = currentProfile,
                nearbyPeers = nearbyPeers,
                isBroadcasting = isBroadcasting,
                onBroadcastingChange = { shouldBroadcast ->
                    coroutineScope.launch {
                        if (shouldBroadcast)
                            appContainer.startBroadcastUseCase(currentProfile)
                        else
                            appContainer.stopBroadcastUseCase()
                    }
                },
                onEditProfileClick = {
                    currentScreen = AppScreen.EditProfile
                },
                onResetProfileClick = {
                    coroutineScope.launch {
                        appContainer.stopBroadcastUseCase()
                        appContainer.clearProfileUseCase()
                    }
                }
            )

            AppScreen.EditProfile -> ProfileEditScreen(
                profile = currentProfile,
                onSaveProfile = { updatedProfile ->
                    coroutineScope.launch {
                        appContainer.saveProfileUseCase(updatedProfile)
                        currentScreen = AppScreen.Home
                    }
                }
            )
        }
}

private enum class AppScreen {
    Home,
    EditProfile
}