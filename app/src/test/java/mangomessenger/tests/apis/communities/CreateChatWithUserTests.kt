package mangomessenger.tests.apis.communities

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class CreateChatWithUserTests {
    private lateinit var context: CommunitiesContext

    @Before
    fun before() {
        context = CommunitiesContext()
    }

    @Test
    fun createChatWithUserSuccess() {
        val userId = UUID.fromString("30082298-e6e6-4aca-a8b9-89ba06d12e4f")
        context.login()
        val response = context.communitiesApi.createChatWithUserAsync(userId).get()
        Assert.assertTrue(response.success)
    }
}