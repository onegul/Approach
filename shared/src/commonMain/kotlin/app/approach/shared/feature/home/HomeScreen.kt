package app.approach.shared.feature.home

import androidx.compose.runtime.Composable
import app.approach.shared.core.model.NearbyCapabilityState
import app.approach.shared.core.model.NearbyPeer
import app.approach.shared.core.model.NearbyPermissionState
import app.approach.shared.core.model.UserProfile
import app.approach.shared.feature.discovery.DiscoveryScreen
import app.approach.shared.feature.discovery.DiscoveryUiState

@Composable
fun HomeScreen(
    profile: UserProfile,
    nearbyPeers: List<NearbyPeer>,
    isBroadcasting: Boolean,
    nearbyCapabilityState: NearbyCapabilityState,
    permissionState: NearbyPermissionState,
    onBroadcastingChange: (Boolean) -> Unit,
    onRequestPermissionClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onResetProfileClick: () -> Unit
) {
    DiscoveryScreen(
        uiState = DiscoveryUiState(
            profile = profile,
            nearbyPeers = nearbyPeers,
            isBroadcasting = isBroadcasting,
            capabilityState = nearbyCapabilityState,
            permissionState = permissionState
        ),
        onBroadcastingChange = onBroadcastingChange,
        onRequestPermissionClick = onRequestPermissionClick,
        onEditProfileClick = onEditProfileClick,
        onResetProfileClick = onResetProfileClick
    )
}