package app.approach.shared.data.repository

import app.approach.shared.core.model.AvatarColor
import app.approach.shared.core.model.UserProfile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class InMemoryProfileRepositoryTest {
    @Test
    fun getProfile_returnsInitialProfile() = runTest {
        val profile = profile()
        val repository = InMemoryProfileRepository(initialProfile = profile)

        assertEquals(profile, repository.getProfile().first())
    }

    @Test
    fun saveProfile_updatesObservedProfile() = runTest {
        val repository = InMemoryProfileRepository()

        val profile = profile(displayName = "Mira")
        repository.saveProfile(profile)

        assertEquals(profile, repository.getProfile().first())
    }

    @Test
    fun clearProfile_removesObservedProfile() = runTest {
        val repository = InMemoryProfileRepository(initialProfile = profile())

        repository.clearProfile()

        assertNull(repository.getProfile().first())
    }

    private fun profile(
        displayName: String = "Kabir"
    ) = UserProfile(
        id = "local-user",
        displayName = displayName,
        bio = "Writing heuristics of wisdom people call dohe.",
        avatarColor = AvatarColor.Blue,
        interests = listOf("Love", "Spirituality")
    )
}