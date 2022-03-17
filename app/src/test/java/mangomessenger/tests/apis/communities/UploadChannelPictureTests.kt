package mangomessenger.tests.apis.communities

import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.*

class UploadChannelPictureTests {
    private lateinit var context: CommunitiesContext

    @Before
    fun before() {
        context = CommunitiesContext()
    }

    @Test
    fun uploadChannelPictureSuccess() {
        // TODO: Rewrite this test to use mocked UUID and File.
        // TODO: Probably use relative filePath.
        context.login()
        val chatId = UUID.fromString("fe3a4980-f508-4604-8788-c64e74702b23")
        val file = File("C:\\Users\\#hash#\\Pictures\\mangoicon.png")
        val response = context.communitiesApi.uploadChannelPictureAsync(chatId, file).get()
        Assert.assertTrue(response.success)
    }
}