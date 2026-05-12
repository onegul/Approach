package app.approach.shared.feature.discovery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import app.approach.shared.core.designsystem.component.ApproachButton
import app.approach.shared.core.designsystem.component.ApproachOutlineButton
import app.approach.shared.core.designsystem.component.StatusPill
import app.approach.shared.core.model.DistanceHint
import app.approach.shared.core.model.NearbyPeer
import app.approach.shared.core.model.NearbyPermissionState

@Composable
fun DiscoveryScreen(
    uiState: DiscoveryUiState,
    onBroadcastingChange: (Boolean) -> Unit,
    onRequestPermissionClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onResetProfileClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier.fillMaxSize()
    ) { contentPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                DiscoveryHeader(
                    displayName = uiState.profile.displayName,
                    isBroadcasting = uiState.isBroadcasting,
                    permissionState = uiState.permissionState,
                    onBroadcastingChange = onBroadcastingChange,
                    onRequestPermissionClick = onRequestPermissionClick,
                    onEditProfileClick = onEditProfileClick,
                    onResetProfileClick = onResetProfileClick
                )
            }

            if (uiState.nearbyPeers.isEmpty()) {
                item {
                    EmptyDiscoveryState()
                }
            } else {
                items(
                    items = uiState.nearbyPeers,
                    key = { it.id }
                ) { peer ->
                    NearbyPeerCard(peer = peer)
                }
            }
        }
    }
}

@Composable
private fun DiscoveryHeader(
    displayName: String,
    isBroadcasting: Boolean,
    permissionState: NearbyPermissionState,
    onBroadcastingChange: (Boolean) -> Unit,
    onRequestPermissionClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onResetProfileClick: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        when (permissionState) {
            NearbyPermissionState.Granted ->
                StatusPill(
                    text = if (isBroadcasting) "Broadcasting locally" else "Not broadcasting"
                )

            NearbyPermissionState.Unknown,
            NearbyPermissionState.Denied ->
                ApproachButton(
                    text = "Allow nearby discovery",
                    onClick = onRequestPermissionClick
                )

            NearbyPermissionState.PermanentlyDenied ->
                Text(
                    text = "Nearby discovery permission is disabled. Enable it in system settings.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )

            NearbyPermissionState.Unavailable ->
                Text(
                    text = "Nearby discovery is not available on this device.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error
                )
        }

        Text(
            text = "Nearby",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Hi, $displayName. Choose when your public profile is visible to nearby people.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        ApproachOutlineButton(
            text = "Edit profile",
            onClick = onEditProfileClick
        )

        ApproachOutlineButton(
            text = "Reset profile",
            onClick = onResetProfileClick
        )

        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Broadcast profile",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = "Use local device discovery only.",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Switch(
                    checked = isBroadcasting,
                    onCheckedChange = onBroadcastingChange,
                    enabled = permissionState == NearbyPermissionState.Granted
                )
            }
        }
    }
}

@Composable
private fun EmptyDiscoveryState() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "No one nearby yet",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "When nearby profiles are discovered, they will appear here.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun NearbyPeerCard(
    peer: NearbyPeer
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = peer.publicProfile.displayName,
                        style = MaterialTheme.typography.titleMedium
                    )

                    if (peer.publicProfile.bio.isNotBlank()) {
                        Text(
                            text = peer.publicProfile.bio,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                AssistChip(
                    onClick = {},
                    label = {
                        Text(peer.distanceHint.label)
                    }
                )
            }

            if (peer.publicProfile.interests.isNotEmpty()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    peer.publicProfile.interests.forEach { interest ->
                        AssistChip(
                            onClick = {},
                            label = { Text(interest) }
                        )
                    }
                }
            }
        }
    }
}

private val DistanceHint.label: String
    get() = when (this) {
        DistanceHint.Immediate -> "Very close"
        DistanceHint.Near -> "Nearby"
        DistanceHint.Around -> "Around"
    }