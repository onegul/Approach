package app.approach.shared.data.nearby

import app.approach.shared.core.model.NearbyPermissionState
import app.approach.shared.domain.nearby.NearbyPermissionController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeNearbyPermissionController(
    initialState: NearbyPermissionState = NearbyPermissionState.Granted
) : NearbyPermissionController {
    private val permissionState = MutableStateFlow(initialState)

    override fun observePermissionState(): Flow<NearbyPermissionState> =
        permissionState.asStateFlow()

    override suspend fun requestPermission() {
        permissionState.value = NearbyPermissionState.Granted
    }
}