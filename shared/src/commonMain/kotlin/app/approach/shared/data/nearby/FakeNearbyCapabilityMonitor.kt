package app.approach.shared.data.nearby

import app.approach.shared.core.model.NearbyCapabilityState
import app.approach.shared.domain.nearby.NearbyCapabilityMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeNearbyCapabilityMonitor(
    initialState: NearbyCapabilityState = NearbyCapabilityState.Available
) : NearbyCapabilityMonitor {
    private val state = MutableStateFlow(initialState)

    override fun observeCapabilityState(): Flow<NearbyCapabilityState> =
        state.asStateFlow()
}