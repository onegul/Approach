package app.approach.shared.domain.usecase

import app.approach.shared.domain.nearby.NearbyCapabilityMonitor

class ObserveNearbyCapabilityStateUseCase(
    private val nearbyCapabilityMonitor: NearbyCapabilityMonitor
) {
    operator fun invoke() = nearbyCapabilityMonitor.observeCapabilityState()
}