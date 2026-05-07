package app.approach.shared

import app.approach.shared.data.repository.InMemoryProfileRepository
import app.approach.shared.domain.repository.ProfileRepository
import app.approach.shared.domain.usecase.ObserveProfileUseCase
import app.approach.shared.domain.usecase.SaveProfileUseCase

class AppContainer {
    private val profileRepository: ProfileRepository = InMemoryProfileRepository()

    val observeProfileUseCase = ObserveProfileUseCase(profileRepository)
    val saveProfileUseCase = SaveProfileUseCase(profileRepository)
}