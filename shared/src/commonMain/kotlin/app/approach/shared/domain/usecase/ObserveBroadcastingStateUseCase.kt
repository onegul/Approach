package app.approach.shared.domain.usecase

import app.approach.shared.domain.nearby.NearbyBroadcaster

class ObserveBroadcastingStateUseCase(
    private val nearbyBroadcaster: NearbyBroadcaster
) {
    operator fun invoke() = nearbyBroadcaster.isBroadcasting
}