package app.approach.shared.domain.usecase

import app.approach.shared.domain.nearby.NearbyPermissionController

class ObserveNearbyPermissionStateUseCase(
    private val nearbyPermissionController: NearbyPermissionController
) {
    operator fun invoke() = nearbyPermissionController.observePermissionState()
}