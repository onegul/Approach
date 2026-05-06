package app.approach.shared.core.model

fun UserProfile.toPublicProfile(broadcastId: String): PublicProfile = PublicProfile(
    broadcastId = broadcastId,
    displayName = displayName,
    bio = bio,
    avatarColor = avatarColor,
    interests = interests
)