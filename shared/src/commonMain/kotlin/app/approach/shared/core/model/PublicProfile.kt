package app.approach.shared.core.model

data class PublicProfile(
    val broadcastId: String,
    val displayName: String,
    val bio: String,
    val avatarColor: AvatarColor,
    val interests: List<String>
)
