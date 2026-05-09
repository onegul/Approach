package app.approach.shared.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import app.approach.shared.core.database.entity.UserProfileEntity
import kotlinx.coroutines.flow.Flow

private const val LocalProfileId = "local-user"

@Dao
interface ProfileDao {
    @Query("SELECT * FROM user_profiles WHERE id = :id LIMIT 1")
    fun getProfile(id: String = LocalProfileId): Flow<UserProfileEntity?>

    @Upsert
    suspend fun upsertProfile(profile: UserProfileEntity)

    @Query("DELETE FROM user_profiles WHERE id = :id")
    suspend fun deleteProfile(id: String = LocalProfileId)
}