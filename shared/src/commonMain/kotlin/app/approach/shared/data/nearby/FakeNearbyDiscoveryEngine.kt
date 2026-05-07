package app.approach.shared.data.nearby

import app.approach.shared.core.model.AvatarColor
import app.approach.shared.core.model.DistanceHint
import app.approach.shared.core.model.NearbyPeer
import app.approach.shared.core.model.PublicProfile
import app.approach.shared.domain.nearby.NearbyBroadcaster
import app.approach.shared.domain.nearby.NearbyScanner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeNearbyDiscoveryEngine : NearbyScanner, NearbyBroadcaster {
    private val broadcasting = MutableStateFlow(false)

    private val nearbyPeers = MutableStateFlow(
        listOf(
            NearbyPeer(
                id = "peer-1",
                publicProfile = PublicProfile(
                    broadcastId = "broadcast-1",
                    displayName = "Cicero",
                    bio = "Writing a book on personal duties.",
                    avatarColor = AvatarColor.Mint,
                    interests = listOf("Epistemology", "Ethics", "Politics")
                ),
                distanceHint = DistanceHint.Immediate,
                lastSeenEpochMillis = 0L
            ),
            NearbyPeer(
                id = "peer-2",
                publicProfile = PublicProfile(
                    broadcastId = "broadcast-2",
                    displayName = "Dostoevsky",
                    bio = "Reading souls of humans.",
                    avatarColor = AvatarColor.Violet,
                    interests = listOf("Faith", "Philosophy")
                ),
                distanceHint = DistanceHint.Near,
                lastSeenEpochMillis = 0L
            ),
            NearbyPeer(
                id = "peer-3",
                publicProfile = PublicProfile(
                    broadcastId = "broadcast-3",
                    displayName = "Steve Jobs",
                    bio = "Building useful things",
                    avatarColor = AvatarColor.Coral,
                    interests = listOf("Technology", "Art")
                ),
                distanceHint = DistanceHint.Around,
                lastSeenEpochMillis = 0L
            )
        )
    )

    override val isBroadcasting: Flow<Boolean> = broadcasting.asStateFlow()

    override fun observeNearbyPeers(): Flow<List<NearbyPeer>> = nearbyPeers.asStateFlow()

    override suspend fun startBroadcast(profile: PublicProfile) {
        broadcasting.value = true
    }

    override suspend fun stopBroadcast() {
        broadcasting.value = false
    }
}