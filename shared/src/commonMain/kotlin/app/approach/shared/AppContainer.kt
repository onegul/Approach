package app.approach.shared

import app.approach.shared.data.nearby.FakeNearbyDiscoveryEngine
import app.approach.shared.data.repository.InMemoryProfileRepository
import app.approach.shared.domain.nearby.NearbyBroadcaster
import app.approach.shared.domain.nearby.NearbyScanner
import app.approach.shared.domain.repository.ProfileRepository
import app.approach.shared.domain.usecase.ObserveBroadcastingStateUseCase
import app.approach.shared.domain.usecase.ObserveNearbyPeersUseCase
import app.approach.shared.domain.usecase.ObserveProfileUseCase
import app.approach.shared.domain.usecase.SaveProfileUseCase
import app.approach.shared.domain.usecase.StartBroadcastUseCase
import app.approach.shared.domain.usecase.StopBroadcastUseCase

class AppContainer {
    private val profileRepository: ProfileRepository = InMemoryProfileRepository()
    private val nearbyDiscoveryEngine = FakeNearbyDiscoveryEngine()

    val nearbyScanner: NearbyScanner = nearbyDiscoveryEngine
    val nearbyBroadcaster: NearbyBroadcaster = nearbyDiscoveryEngine

    val observeNearbyPeersUseCase = ObserveNearbyPeersUseCase(nearbyScanner)
    val observeBroadcastingStateUseCase = ObserveBroadcastingStateUseCase(nearbyBroadcaster)
    val startBroadcastUseCase = StartBroadcastUseCase(nearbyBroadcaster)
    val stopBroadcastUseCase = StopBroadcastUseCase(nearbyBroadcaster)

    val observeProfileUseCase = ObserveProfileUseCase(profileRepository)
    val saveProfileUseCase = SaveProfileUseCase(profileRepository)
}