package app.approach.shared.nearby

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import app.approach.shared.core.model.NearbyPermissionState
import app.approach.shared.domain.nearby.NearbyPermissionController
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class AndroidNearbyPermissionController(
    private val activity: ComponentActivity
) : NearbyPermissionController {
    private var hasRequestedPermission = false
    private var pendingRequest: CompletableDeferred<Unit>? = null

    private val permissionState = MutableStateFlow(resolvePermissionState())

    private val permissionLauncher = activity.registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { result ->
        permissionState.value = resolvePermissionState(result)
        pendingRequest?.complete(Unit)
        pendingRequest = null
    }

    override fun observePermissionState(): Flow<NearbyPermissionState> {
        permissionState.value = resolvePermissionState()
        return permissionState.asStateFlow()
    }

    override suspend fun requestPermission() {
        val permissions = requiredNearbyPermissions()

        if (permissions.isEmpty()) {
            permissionState.value = NearbyPermissionState.Granted
            return
        }

        if (resolvePermissionState() == NearbyPermissionState.Granted) {
            permissionState.value = NearbyPermissionState.Granted
            return
        }

        hasRequestedPermission = true

        val request = CompletableDeferred<Unit>()
        pendingRequest = request
        permissionLauncher.launch(permissions.toTypedArray())
        request.await()
    }

    private fun resolvePermissionState(
        latestResult: Map<String, Boolean>? = null
    ): NearbyPermissionState {
        val permissions = requiredNearbyPermissions()

        if (permissions.isEmpty())
            return NearbyPermissionState.Granted

        val allGranted = permissions.all { permission ->
            activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        }

        if (allGranted)
            return NearbyPermissionState.Granted

        val resultDenied = latestResult?.values?.any { granted -> !granted } == true
        val permanentlyDenied = hasRequestedPermission &&
                resultDenied &&
                permissions.any { permission ->
                    !activity.shouldShowRequestPermissionRationale(permission)
                }

        return if (permanentlyDenied)
            NearbyPermissionState.PermanentlyDenied
        else
            NearbyPermissionState.Denied
    }

    private fun requiredNearbyPermissions(): List<String> {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> listOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.NEARBY_WIFI_DEVICES
            )

            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> listOf(
                Manifest.permission.BLUETOOTH_SCAN,
                Manifest.permission.BLUETOOTH_CONNECT,
                Manifest.permission.BLUETOOTH_ADVERTISE,
                Manifest.permission.ACCESS_FINE_LOCATION
            )

            else -> emptyList()
        }
    }
}