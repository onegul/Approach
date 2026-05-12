package app.approach.shared.feature.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
fun ProfileEditor(
    title: String,
    description: String,
    buttonText: String,
    initialProfile: UserProfile?,
    onSaveProfile: (UserProfile) -> Unit,
    modifier: Modifier = Modifier
) {
    val nameState = rememberTextFieldState(initialProfile?.displayName.orEmpty())
    val bioState = rememberTextFieldState(initialProfile?.bio.orEmpty())
    val selectedInterests = remember {
        mutableStateListOf<String>().apply {
            addAll(initialProfile?.interests.orEmpty())
        }
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = description,
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
                        if (!selected)
                            selectedInterests.add(interest)
                        else
                            selectedInterests.remove(interest)
                    },
                    label = { Text(interest) }
                )
            }
        }

        ApproachButton(
            text = buttonText,
            onClick = {
                onSaveProfile(
                    UserProfile(
                        id = initialProfile?.id ?: "local-user",
                        displayName = nameState.text.toString(),
                        bio = bioState.text.toString(),
                        avatarColor = initialProfile?.avatarColor ?: AvatarColor.Blue,
                        interests = selectedInterests.toList()
                    )
                )
            },
            enabled = nameState.text.isNotBlank()
        )
    }
}