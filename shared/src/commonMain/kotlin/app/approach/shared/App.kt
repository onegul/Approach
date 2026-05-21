package app.approach.shared

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.approach.shared.core.designsystem.theme.ApproachTheme
import app.approach.shared.core.model.NearbyCapabilityState
import app.approach.shared.core.model.NearbyPermissionState
import app.approach.shared.core.navigation.AppDestination
import app.approach.shared.core.navigation.rememberAppNavigator
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
    val navigator = rememberAppNavigator()
    val appContainer = LocalAppContainer.current
    val coroutineScope = rememberCoroutineScope()

    val profile by appContainer
        .observeProfileUseCase()
        .collectAsStateWithLifecycle(initialValue = null)

    val nearbyCapabilityState by appContainer
        .observeNearbyCapabilityStateUseCase()
        .collectAsStateWithLifecycle(initialValue = NearbyCapabilityState.Unknown)

    val permissionState by appContainer
        .observeNearbyPermissionStateUseCase()
        .collectAsStateWithLifecycle(initialValue = NearbyPermissionState.Unknown)

    val nearbyPeers by appContainer
        .observeNearbyPeersUseCase()
        .collectAsStateWithLifecycle(initialValue = emptyList())

    val isBroadcasting by appContainer
        .observeBroadcastingStateUseCase()
        .collectAsStateWithLifecycle(initialValue = false)

    val currentProfile = profile

    if (currentProfile == null)
        ProfileSetupScreen(
            onSaveProfile = { profile ->
                coroutineScope.launch {
                    appContainer.saveProfileUseCase(profile)
                    navigator.replace(AppDestination.Home)
                }
            }
        )
    else
        when (navigator.currentDestination) {
            AppDestination.Home -> HomeScreen(
                profile = currentProfile,
                nearbyPeers = nearbyPeers,
                isBroadcasting = isBroadcasting,
                permissionState = permissionState,
                nearbyCapabilityState = nearbyCapabilityState,
                onBroadcastingChange = { shouldBroadcast ->
                    coroutineScope.launch {
                        if (shouldBroadcast)
                            appContainer.startBroadcastUseCase(currentProfile)
                        else
                            appContainer.stopBroadcastUseCase()
                    }
                },
                onRequestPermissionClick = {
                    coroutineScope.launch {
                        appContainer.requestNearbyPermissionUseCase()
                    }
                },
                onEditProfileClick = {
                    navigator.navigate(AppDestination.EditProfile)
                },
                onResetProfileClick = {
                    coroutineScope.launch {
                        appContainer.stopBroadcastUseCase()
                        appContainer.clearProfileUseCase()
                        navigator.replace(AppDestination.Home)
                    }
                }
            )

            AppDestination.EditProfile -> ProfileEditScreen(
                profile = currentProfile,
                onSaveProfile = { updatedProfile ->
                    coroutineScope.launch {
                        appContainer.saveProfileUseCase(updatedProfile)
                        navigator.navigateBack()
                    }
                },
                onBackClick = {
                    navigator.navigateBack()
                }
            )
        }
}