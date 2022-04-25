package mangomessenger.http

import java.io.File
import java.io.OutputStream

class MultipartFormFile(
    val name: String,
    val fileName: String,
    val contentType: String,
    private val file: File
    ) : MultipartFormVariable {

    private companion object {
        const val bufferSize = 1024 * 4
    }

    override fun writeToStream(outputStream: OutputStream) {
        outputStream.writer().run {
            write("Content-Disposition: form-data; name=\"$name\"; filename=\"$fileName\"")
            write("\r\n")
            write("Content-Type: $contentType")
            write("\r\n")
            write("\r\n")
            flush()
        }

        var length: Int
        val buffer = ByteArray(bufferSize)
        val inputStream = file.inputStream()
        while (inputStream.available() > 0) {
            length = inputStream.read(buffer, 0, bufferSize)
            outputStream.run {
                write(buffer, 0, length)
                flush()
            }
        }
        inputStream.close()
    }
}