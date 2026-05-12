package app.approach.shared.feature.profile

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.approach.shared.core.model.UserProfile

@Composable
fun ProfileEditScreen(
    profile: UserProfile,
    onSaveProfile: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) {
        ProfileEditor(
            title = "Edit profile",
            description = "Update what nearby people can see when you broadcast",
            buttonText = "Save changes",
            initialProfile = profile,
            onSaveProfile = onSaveProfile,
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(24.dp)
        )
    }
}