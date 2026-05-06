package app.approach.shared.core.model

data class UserProfile(
    val id: String,
    val displayName: String,
    val bio: String,
    val avatarColor: AvatarColor,
    val interests: List<String>
) {
    val isComplete: Boolean
        get() = displayName.isNotBlank()
}