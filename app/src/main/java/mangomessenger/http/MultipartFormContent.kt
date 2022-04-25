package mangomessenger.http

import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class MultipartFormContent(val boundary: String = UUID.randomUUID().toString()) : HttpContent {
    val formFields = ArrayList<MultipartFormVariable>()

    private companion object {
        const val lineSeparator = "\r\n"
    }

    override fun writeToStream(outputStream: OutputStream) {
        formFields.forEach {
            outputStream.writer().run {
                write("--$boundary")
                write(lineSeparator)
                flush()
            }

            it.writeToStream(outputStream)
        }

        outputStream.writer().run {
            write(lineSeparator)
            write("--$boundary--")
            flush()
        }
    }
}