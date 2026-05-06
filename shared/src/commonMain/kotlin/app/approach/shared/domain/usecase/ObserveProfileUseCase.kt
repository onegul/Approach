package app.approach.shared.domain.usecase

import app.approach.shared.domain.repository.ProfileRepository

class ObserveProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    operator fun invoke() = profileRepository.profile
}