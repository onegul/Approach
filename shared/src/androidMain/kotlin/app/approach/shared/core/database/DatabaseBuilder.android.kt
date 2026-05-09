package app.approach.shared.core.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

fun createDatabaseBuilder(context: Context): RoomDatabase.Builder<ApproachDatabase> {
    val appContext = context.applicationContext
    val databaseFile = appContext.getDatabasePath("approach.db")

    return Room.databaseBuilder<ApproachDatabase>(
        context = appContext,
        name = databaseFile.absolutePath
    )
}