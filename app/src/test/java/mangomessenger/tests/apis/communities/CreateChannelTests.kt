package mangomessenger.tests.apis.communities

import mangomessenger.core.apis.requests.CreateCommunityRequest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.util.*

class CreateChannelTests {
    private lateinit var context: CommunitiesContext

    @Before
    fun before() {
        context = CommunitiesContext()
    }

    @Test
    fun createChannelSuccess() {
        context.login()
        val channelTitle = UUID.randomUUID().toString()
        val channelDescription = UUID.randomUUID().toString()
        val createCommunityRequest = CreateCommunityRequest(channelTitle, channelDescription)
        val response = context.communitiesApi.createChannelAsync(createCommunityRequest).get()
        Assert.assertTrue(response.success)
    }
}