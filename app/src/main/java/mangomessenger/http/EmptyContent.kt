package mangomessenger.http

import java.io.OutputStream

class EmptyContent : HttpContent {
    override fun writeToStream(outputStream: OutputStream) {
        outputStream.flush()
    }
}