package app.approach.shared.domain.nearby

import app.approach.shared.core.model.NearbyCapabilityState
import kotlinx.coroutines.flow.Flow

interface NearbyCapabilityMonitor {
    fun observeCapabilityState(): Flow<NearbyCapabilityState>
}