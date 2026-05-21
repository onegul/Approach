package app.approach.shared.nearby

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Build
import app.approach.shared.core.model.NearbyCapabilityState
import app.approach.shared.domain.nearby.NearbyCapabilityMonitor
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class AndroidNearbyCapabilityMonitor(
    private val context: Context
) : NearbyCapabilityMonitor {
    private val appContext = context.applicationContext

    override fun observeCapabilityState(): Flow<NearbyCapabilityState> = callbackFlow {
        fun sendCurrentState() = trySend(resolveCapabilityState())

        val receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                if (intent?.action == BluetoothAdapter.ACTION_STATE_CHANGED)
                    sendCurrentState()
            }
        }

        sendCurrentState()

        val filter = IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            appContext.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED)
        else
            appContext.registerReceiver(receiver, filter)

        awaitClose {
            appContext.unregisterReceiver(receiver)
        }
    }.distinctUntilChanged()

    private fun resolveCapabilityState(): NearbyCapabilityState {
        val packageManager = appContext.packageManager

        val hasBluetoothLe = packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)

        if (!hasBluetoothLe)
            return NearbyCapabilityState.BluetoothUnavailable

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
            appContext.checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        )
            return NearbyCapabilityState.PermissionRequired

        val bluetoothManager = appContext.getSystemService(BluetoothManager::class.java)
        val adapter = bluetoothManager?.adapter ?: return NearbyCapabilityState.BluetoothUnavailable

        return if (adapter.isEnabled)
            NearbyCapabilityState.Available
        else
            NearbyCapabilityState.BluetoothDisabled
    }
}