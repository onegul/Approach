package app.approach.shared.domain.usecase

import app.approach.shared.core.model.UserProfile
import app.approach.shared.domain.repository.ProfileRepository

class SaveProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(profile: UserProfile) {
        require(profile.displayName.isNotBlank()) { "Display name cannot be blank." }

        profileRepository.saveProfile(profile)
    }
}