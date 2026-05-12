package app.approach.shared.core.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember

@Stable
class AppNavigator(
    initialDestination: AppDestination = AppDestination.Home
) {
    private val backStack = mutableStateListOf(initialDestination)

    val currentDestination: AppDestination
        get() = backStack.last()

    fun navigate(destination: AppDestination) {
        if (currentDestination != destination)
            backStack.add(destination)
    }

    fun replace(destination: AppDestination) {
        backStack.clear()
        backStack.add(destination)
    }

    fun navigateBack(): Boolean {
        if (backStack.size <= 1)
            return false

        backStack.removeAt(backStack.lastIndex)
        return true
    }
}

@Composable
fun rememberAppNavigator(
    initialDestination: AppDestination = AppDestination.Home
): AppNavigator =
    remember { AppNavigator(initialDestination) }