package app.approach.shared.core.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import app.approach.shared.core.database.dao.ProfileDao
import app.approach.shared.core.database.entity.UserProfileEntity

@Database(
    entities = [UserProfileEntity::class],
    version = 1
)
@ConstructedBy(ApproachDatabaseConstructor::class)
abstract class ApproachDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
}

@Suppress("KotlinNoActualForExpect")
expect object ApproachDatabaseConstructor : RoomDatabaseConstructor<ApproachDatabase> {
    override fun initialize(): ApproachDatabase
}