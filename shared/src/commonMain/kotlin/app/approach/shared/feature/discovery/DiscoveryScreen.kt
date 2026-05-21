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
import app.approach.shared.core.model.NearbyCapabilityState
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
                    capabilityState = uiState.capabilityState,
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
    capabilityState: NearbyCapabilityState,
    onBroadcastingChange: (Boolean) -> Unit,
    onRequestPermissionClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onResetProfileClick: () -> Unit
) {
    val canBroadcast =
        permissionState == NearbyPermissionState.Granted &&
                capabilityState == NearbyCapabilityState.Available

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        StatusPill(
            text = when {
                isBroadcasting -> "Broadcasting locally"
                canBroadcast -> "Ready for local discovery"
                permissionState != NearbyPermissionState.Granted -> "Permission needed"
                capabilityState == NearbyCapabilityState.BluetoothDisabled -> "Bluetooth off"
                capabilityState == NearbyCapabilityState.BluetoothUnavailable -> "Bluetooth unavailable"
                else -> "Checking nearby discovery"
            }
        )

        Text(
            text = "Nearby",
            style = MaterialTheme.typography.headlineMedium
        )

        Text(
            text = "Hi, $displayName. Choose when your public profile is visible to nearby people.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        NearbyReadinessMessage(
            permissionState = permissionState,
            capabilityState = capabilityState,
            onRequestPermissionClick = onRequestPermissionClick
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
                    enabled = canBroadcast
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ApproachOutlineButton(
                text = "Edit profile",
                onClick = onEditProfileClick,
                modifier = Modifier.weight(1f)
            )

            ApproachOutlineButton(
                text = "Reset profile",
                onClick = onResetProfileClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun NearbyReadinessMessage(
    permissionState: NearbyPermissionState,
    capabilityState: NearbyCapabilityState,
    onRequestPermissionClick: () -> Unit
) {
    when {
        permissionState == NearbyPermissionState.Unknown ||
                permissionState == NearbyPermissionState.Denied -> {
            ApproachButton(
                text = "Allow nearby discovery",
                onClick = onRequestPermissionClick,
                modifier = Modifier.fillMaxWidth()
            )
        }

        permissionState == NearbyPermissionState.PermanentlyDenied -> {
            ReadinessText(
                text = "Nearby discovery permission is disabled. Enable it in system settings.",
                isError = true
            )
        }

        permissionState == NearbyPermissionState.Unavailable -> {
            ReadinessText(
                text = "Nearby permissions are not available on this device.",
                isError = true
            )
        }

        capabilityState == NearbyCapabilityState.PermissionRequired -> {
            ApproachButton(
                text = "Allow nearby discovery",
                onClick = onRequestPermissionClick,
                modifier = Modifier.fillMaxWidth()
            )
        }

        capabilityState == NearbyCapabilityState.BluetoothUnavailable -> {
            ReadinessText(
                text = "Bluetooth LE is not available on this device.",
                isError = true
            )
        }

        capabilityState == NearbyCapabilityState.BluetoothDisabled -> {
            ReadinessText(
                text = "Turn on Bluetooth to use nearby discovery.",
                isError = false
            )
        }

        capabilityState == NearbyCapabilityState.Unknown -> {
            ReadinessText(
                text = "Checking nearby discovery capabilities...",
                isError = false
            )
        }

        else -> Unit
    }
}

@Composable
private fun ReadinessText(
    text: String,
    isError: Boolean,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.bodyMedium,
        color = if (isError) {
            MaterialTheme.colorScheme.error
        } else {
            MaterialTheme.colorScheme.onSurfaceVariant
        }
    )
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