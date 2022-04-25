package mangomessenger.http

import java.io.OutputStream
import java.util.*
import kotlin.collections.ArrayList

class MultipartFormContent(val boundary: String = UUID.randomUUID().toString()) : HttpContent {
    val formFields = ArrayList<MultipartFormVariable>()

    override fun writeToStream(outputStream: OutputStream) {
        formFields.forEach {
            outputStream.writer().run {
                write("--$boundary")
                write("\r\n")
                flush()
            }

            it.writeToStream(outputStream)
        }

        outputStream.writer().run {
            write("\r\n")
            write("--$boundary--")
            flush()
        }
    }
}