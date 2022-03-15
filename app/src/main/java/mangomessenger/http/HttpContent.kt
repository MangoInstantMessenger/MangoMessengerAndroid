package mangomessenger.http

import java.io.OutputStream

interface HttpContent {
    fun writeToStream(outputStream: OutputStream)
}