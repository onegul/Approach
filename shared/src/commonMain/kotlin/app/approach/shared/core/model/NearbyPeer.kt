package app.approach.shared.core.model

data class NearbyPeer(
    val id: String,
    val publicProfile: PublicProfile,
    val distanceHint: DistanceHint,
    val lastSeenEpochMillis: Long
)

enum class DistanceHint {
    Immediate,
    Near,
    Around
}