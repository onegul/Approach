package app.approach.shared.domain.nearby

import app.approach.shared.core.model.PublicProfile
import kotlinx.coroutines.flow.Flow

interface NearbyBroadcaster {
    val isBroadcasting: Flow<Boolean>

    suspend fun startBroadcast(profile: PublicProfile)

    suspend fun stopBroadcast()
}