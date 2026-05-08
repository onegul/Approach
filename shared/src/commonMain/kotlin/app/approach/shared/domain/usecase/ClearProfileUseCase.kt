package app.approach.shared.domain.usecase

import app.approach.shared.domain.repository.ProfileRepository

class ClearProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke() = profileRepository.clearProfile()
}