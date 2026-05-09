package app.approach.shared.domain.usecase

import app.approach.shared.core.model.AvatarColor
import app.approach.shared.core.model.UserProfile
import app.approach.shared.data.repository.InMemoryProfileRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class SaveProfileUseCaseTest {
    @Test
    fun invoke_savesValidProfit() = runTest {
        val repository = InMemoryProfileRepository()
        val useCase = SaveProfileUseCase(repository)

        val profile = profile(displayName = "Mira")
        useCase(profile)

        assertEquals(profile, repository.getProfile().first())
    }

    @Test
    fun invoke_rejectsBlankDisplayName() = runTest {
        val repository = InMemoryProfileRepository()
        val useCase = SaveProfileUseCase(repository)

        assertFailsWith<IllegalArgumentException> {
            useCase(profile(displayName = " "))
        }
    }

    private fun profile(
        displayName: String
    ) = UserProfile(
        id = "local-user",
        displayName = displayName,
        bio = "Turning ideas into apps.",
        avatarColor = AvatarColor.Mint,
        interests = listOf("Kotlin")
    )
}