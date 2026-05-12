package app.approach.shared.core.navigation

sealed interface AppDestination {
    data object Home : AppDestination
    data object EditProfile : AppDestination
}