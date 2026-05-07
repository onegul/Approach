package app.approach.shared.domain.nearby

import app.approach.shared.core.model.NearbyPeer
import kotlinx.coroutines.flow.Flow

interface NearbyScanner {
    fun observeNearbyPeers(): Flow<List<NearbyPeer>>
}