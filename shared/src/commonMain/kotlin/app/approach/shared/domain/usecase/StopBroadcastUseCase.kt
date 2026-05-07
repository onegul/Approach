package app.approach.shared.domain.usecase

import app.approach.shared.domain.nearby.NearbyBroadcaster

class StopBroadcastUseCase(
    private val nearbyBroadcaster: NearbyBroadcaster
) {
    suspend operator fun invoke() = nearbyBroadcaster.stopBroadcast()
}