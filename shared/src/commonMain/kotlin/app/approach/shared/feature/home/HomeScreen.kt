package app.approach.shared.feature.home

import androidx.compose.runtime.Composable
import app.approach.shared.core.model.NearbyPeer
import app.approach.shared.core.model.UserProfile
import app.approach.shared.feature.discovery.DiscoveryScreen
import app.approach.shared.feature.discovery.DiscoveryUiState

@Composable
fun HomeScreen(
    profile: UserProfile,
    nearbyPeers: List<NearbyPeer>,
    isBroadcasting: Boolean,
    onBroadcastingChange: (Boolean) -> Unit,
    onResetProfileClick: () -> Unit
) {
    DiscoveryScreen(
        uiState = DiscoveryUiState(
            profile = profile,
            nearbyPeers = nearbyPeers,
            isBroadcasting = isBroadcasting
        ),
        onBroadcastingChange = onBroadcastingChange,
        onResetProfileClick = onResetProfileClick
    )
}