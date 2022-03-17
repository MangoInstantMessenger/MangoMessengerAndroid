package mangomessenger.tests.apis.communities

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetChatsTests {
    private lateinit var context: CommunitiesContext

    @Before
    fun before() {
        context = CommunitiesContext()
    }

    @Test
    fun getChatsSuccess() {
        context.login()
        val getChatsResponse = context.communitiesApi.getChatsAsync().get()
        Assert.assertTrue(getChatsResponse.success)
    }

    @Test
    fun getChatsUnauthenticated() {
        val getChatsResponse = context.communitiesApi.getChatsAsync().get()
        Assert.assertFalse(getChatsResponse.success)
    }
}