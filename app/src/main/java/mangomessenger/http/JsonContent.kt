package mangomessenger.http

import java.io.OutputStream

class JsonContent(private val jsonString: String = String()) : HttpContent {
    override fun writeToStream(outputStream: OutputStream) {
        outputStream.write(jsonString.toByteArray())
        outputStream.flush()
    }

    override fun toString(): String {
        return jsonString
    }
}