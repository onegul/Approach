package app.approach.shared.core.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import app.approach.shared.core.model.AvatarColor
import app.approach.shared.core.model.UserProfile

@Entity(tableName = "user_profiles")
data class UserProfileEntity(
    @PrimaryKey val id: String,
    val displayName: String,
    val bio: String,
    val avatarColor: String,
    val interests: String
)

private const val InterestSeparator = "\u001F"

fun UserProfileEntity.toDomain(): UserProfile = UserProfile(
    id = id,
    displayName = displayName,
    bio = bio,
    avatarColor = AvatarColor.entries.firstOrNull { it.name == avatarColor } ?: AvatarColor.Blue,
    interests = interests
        .split(InterestSeparator)
        .filter { it.isNotBlank() }
)

fun UserProfile.toEntity(): UserProfileEntity = UserProfileEntity(
    id = id,
    displayName = displayName,
    bio = bio,
    avatarColor = avatarColor.name,
    interests = interests.joinToString(InterestSeparator)
)