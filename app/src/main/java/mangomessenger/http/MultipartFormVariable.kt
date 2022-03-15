package mangomessenger.http

import java.io.OutputStream

interface MultipartFormVariable {
    fun writeToStream(outputStream: OutputStream)
}