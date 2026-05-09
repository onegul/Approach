package app.approach.shared.data.repository

import app.approach.shared.core.model.UserProfile
import app.approach.shared.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class InMemoryProfileRepository(
    initialProfile: UserProfile? = null
) : ProfileRepository {
    private val profile = MutableStateFlow(initialProfile)

    override fun getProfile(): Flow<UserProfile?> {
        return profile.asStateFlow()
    }

    override suspend fun saveProfile(profile: UserProfile) {
        this.profile.value = profile
    }

    override suspend fun clearProfile() {
        profile.value = null
    }
}