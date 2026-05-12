package app.approach.shared.feature.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.approach.shared.core.model.UserProfile

@Composable
fun ProfileSetupScreen(
    onSaveProfile: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        ProfileEditor(
            title = "Create your local profile",
            description = "This stays on your device. You choose what nearby people can see.",
            buttonText = "Save profile",
            onSaveProfile = onSaveProfile,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(24.dp)
        )
    }
}