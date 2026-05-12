package app.approach.shared.domain.nearby

import app.approach.shared.core.model.NearbyPermissionState
import kotlinx.coroutines.flow.Flow

interface NearbyPermissionController {
    fun observePermissionState(): Flow<NearbyPermissionState>

    suspend fun requestPermission()
}