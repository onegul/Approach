package app.approach.shared

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import app.approach.shared.core.database.createApproachDatabase
import app.approach.shared.core.database.createDatabaseBuilder
import app.approach.shared.data.repository.LocalProfileRepository
import app.approach.shared.nearby.AndroidNearbyCapabilityMonitor
import app.approach.shared.nearby.AndroidNearbyPermissionController

fun ComponentActivity.setApproachContent() {
    val database = createApproachDatabase(builder = createDatabaseBuilder(applicationContext))

    val appContainer =
        AppContainer(
            profileRepository = LocalProfileRepository(database.profileDao()),
            nearbyPermissionController = AndroidNearbyPermissionController(this),
            nearbyCapabilityMonitor = AndroidNearbyCapabilityMonitor(applicationContext)
        )

    setContent {
        App(appContainer = appContainer)
    }
}