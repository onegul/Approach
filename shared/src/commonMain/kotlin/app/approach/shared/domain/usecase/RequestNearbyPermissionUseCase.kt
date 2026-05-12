package app.approach.shared.domain.usecase

import app.approach.shared.domain.nearby.NearbyPermissionController

class RequestNearbyPermissionUseCase(
    private val nearbyPermissionController: NearbyPermissionController
) {
    suspend operator fun invoke() = nearbyPermissionController.requestPermission()
}