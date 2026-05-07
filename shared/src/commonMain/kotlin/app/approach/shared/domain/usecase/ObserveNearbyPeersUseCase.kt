package app.approach.shared.domain.usecase

import app.approach.shared.domain.nearby.NearbyScanner

class ObserveNearbyPeersUseCase(
    private val nearbyScanner: NearbyScanner
) {
    operator fun invoke() = nearbyScanner.observeNearbyPeers()
}