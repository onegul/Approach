package app.approach.shared

import app.approach.shared.data.nearby.FakeNearbyCapabilityMonitor
import app.approach.shared.data.nearby.FakeNearbyDiscoveryEngine
import app.approach.shared.data.nearby.FakeNearbyPermissionController
import app.approach.shared.data.repository.InMemoryProfileRepository
import app.approach.shared.domain.nearby.NearbyBroadcaster
import app.approach.shared.domain.nearby.NearbyCapabilityMonitor
import app.approach.shared.domain.nearby.NearbyPermissionController
import app.approach.shared.domain.nearby.NearbyScanner
import app.approach.shared.domain.repository.ProfileRepository
import app.approach.shared.domain.usecase.ClearProfileUseCase
import app.approach.shared.domain.usecase.ObserveBroadcastingStateUseCase
import app.approach.shared.domain.usecase.ObserveNearbyCapabilityStateUseCase
import app.approach.shared.domain.usecase.ObserveNearbyPeersUseCase
import app.approach.shared.domain.usecase.ObserveNearbyPermissionStateUseCase
import app.approach.shared.domain.usecase.ObserveProfileUseCase
import app.approach.shared.domain.usecase.RequestNearbyPermissionUseCase
import app.approach.shared.domain.usecase.SaveProfileUseCase
import app.approach.shared.domain.usecase.StartBroadcastUseCase
import app.approach.shared.domain.usecase.StopBroadcastUseCase

class AppContainer(
    profileRepository: ProfileRepository = InMemoryProfileRepository(),
    nearbyPermissionController: NearbyPermissionController = FakeNearbyPermissionController(),
    nearbyCapabilityMonitor: NearbyCapabilityMonitor = FakeNearbyCapabilityMonitor()
) {
    private val nearbyDiscoveryEngine = FakeNearbyDiscoveryEngine()

    val nearbyScanner: NearbyScanner = nearbyDiscoveryEngine
    val nearbyBroadcaster: NearbyBroadcaster = nearbyDiscoveryEngine

    val observeProfileUseCase = ObserveProfileUseCase(profileRepository)
    val saveProfileUseCase = SaveProfileUseCase(profileRepository)
    val clearProfileUseCase = ClearProfileUseCase(profileRepository)

    val observeNearbyCapabilityStateUseCase =
        ObserveNearbyCapabilityStateUseCase(nearbyCapabilityMonitor)

    val observeNearbyPermissionStateUseCase =
        ObserveNearbyPermissionStateUseCase(nearbyPermissionController)
    val requestNearbyPermissionUseCase = RequestNearbyPermissionUseCase(nearbyPermissionController)

    val observeNearbyPeersUseCase = ObserveNearbyPeersUseCase(nearbyScanner)
    val observeBroadcastingStateUseCase = ObserveBroadcastingStateUseCase(nearbyBroadcaster)
    val startBroadcastUseCase = StartBroadcastUseCase(nearbyBroadcaster)
    val stopBroadcastUseCase = StopBroadcastUseCase(nearbyBroadcaster)
}