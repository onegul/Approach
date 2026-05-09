package app.approach.shared.data.repository

import app.approach.shared.core.database.dao.ProfileDao
import app.approach.shared.core.database.entity.toDomain
import app.approach.shared.core.database.entity.toEntity
import app.approach.shared.core.model.UserProfile
import app.approach.shared.domain.repository.ProfileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalProfileRepository(
    private val profileDao: ProfileDao
) : ProfileRepository {
    override fun getProfile(): Flow<UserProfile?> =
        profileDao
            .getProfile()
            .map { entity ->
                entity?.toDomain()
            }

    override suspend fun saveProfile(profile: UserProfile) =
        profileDao.upsertProfile(profile.toEntity())

    override suspend fun clearProfile() = profileDao.deleteProfile()
}