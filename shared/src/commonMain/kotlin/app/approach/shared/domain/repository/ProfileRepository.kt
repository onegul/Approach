package app.approach.shared.domain.repository

import app.approach.shared.core.model.UserProfile
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getProfile(): Flow<UserProfile?>

    suspend fun saveProfile(profile: UserProfile)

    suspend fun clearProfile()
}