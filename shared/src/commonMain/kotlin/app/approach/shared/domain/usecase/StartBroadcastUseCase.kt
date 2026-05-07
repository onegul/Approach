package app.approach.shared.domain.usecase

import app.approach.shared.core.model.UserProfile
import app.approach.shared.core.model.toPublicProfile
import app.approach.shared.domain.nearby.NearbyBroadcaster

class StartBroadcastUseCase(
    private val nearbyBroadcaster: NearbyBroadcaster
) {
    suspend operator fun invoke(profile: UserProfile) {
        nearbyBroadcaster.startBroadcast(
            profile = profile.toPublicProfile(broadcastId = "fake-broadcast-id")
        )
    }
}