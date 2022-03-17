package mangomessenger.tests.apis.communities

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class SearchChatsTests {
    private lateinit var context: CommunitiesContext

    @Before
    fun before() {
        context = CommunitiesContext()
    }

    @Test
    fun searchChatsSuccess() {
        val displayName = "Kotlin"
        context.login()
        val response = context.communitiesApi.searchChatsAsync(displayName).get()
        Assert.assertTrue(response.success)
    }
}