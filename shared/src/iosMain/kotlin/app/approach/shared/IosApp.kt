package app.approach.shared

import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import app.approach.shared.core.database.createApproachDatabase
import app.approach.shared.core.database.createDatabaseBuilder
import app.approach.shared.data.repository.LocalProfileRepository

fun MainViewController() = ComposeUIViewController {
    val appContainer = remember {
        val database = createApproachDatabase(builder = createDatabaseBuilder())

        AppContainer(profileRepository = LocalProfileRepository(database.profileDao()))
    }

    App(appContainer = appContainer)
}