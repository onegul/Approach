package app.approach.shared.feature.discovery

import app.approach.shared.core.model.NearbyCapabilityState
import app.approach.shared.core.model.NearbyPeer
import app.approach.shared.core.model.NearbyPermissionState
import app.approach.shared.core.model.UserProfile

data class DiscoveryUiState(
    val profile: UserProfile,
    val nearbyPeers: List<NearbyPeer>,
    val isBroadcasting: Boolean,
    val capabilityState: NearbyCapabilityState,
    val permissionState: NearbyPermissionState
)
