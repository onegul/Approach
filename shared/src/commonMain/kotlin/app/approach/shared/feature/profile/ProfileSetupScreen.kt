package app.approach.shared.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.approach.shared.core.designsystem.component.ApproachButton
import app.approach.shared.core.model.AvatarColor
import app.approach.shared.core.model.UserProfile

private val SuggestedInterests = listOf(
    "Coffee",
    "Music",
    "Startups",
    "Books",
    "Fitness",
    "Travel",
    "Gaming",
    "Design"
)

@Composable
fun ProfileSetupScreen(
    onSaveProfile: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    val nameState = rememberTextFieldState()
    val bioState = rememberTextFieldState()
    val selectedInterests = remember { mutableStateListOf<String>() }

    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Text(
                text = "Create your local profile",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "This stays on your device. You choose what nearby people can see.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            OutlinedTextField(
                state = nameState,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Display name") },
                lineLimits = TextFieldLineLimits.SingleLine
            )

            OutlinedTextField(
                state = bioState,
                modifier = Modifier.fillMaxWidth(),
                label = { Text("Short bio") },
                lineLimits = TextFieldLineLimits.MultiLine(minHeightInLines = 3)
            )

            Text(
                text = "Interests",
                style = MaterialTheme.typography.titleMedium
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SuggestedInterests.forEach { interest ->
                    val selected = interest in selectedInterests

                    FilterChip(
                        selected = selected,
                        onClick = {
                            if (selected)
                                selectedInterests.remove(interest)
                            else
                                selectedInterests.add(interest)
                        },
                        label = { Text(interest) }
                    )
                }
            }

            AssistChip(
                onClick = {},
                label = { Text("Broadcast identity will rotate later") }
            )

            ApproachButton(
                text = "Save profile",
                onClick = {
                    onSaveProfile(
                        UserProfile(
                            id = "local-user",
                            displayName = nameState.text.toString().trim(),
                            bio = bioState.text.toString().trim(),
                            avatarColor = AvatarColor.Blue,
                            interests = selectedInterests.toList()
                        )
                    )
                },
                enabled = nameState.text.isNotBlank()
            )
        }
    }
}