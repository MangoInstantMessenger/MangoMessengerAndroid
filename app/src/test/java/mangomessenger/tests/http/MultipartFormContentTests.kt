package mangomessenger.tests.http

import mangomessenger.http.MultipartFormContent
import mangomessenger.http.MultipartFormFile
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.*
import java.lang.StringBuilder
import java.util.*

class MultipartFormContentTests {

    private lateinit var byteArray: ByteArray
    private lateinit var boundary: String
    private lateinit var outputStream: ByteArrayOutputStream
    private lateinit var file: File

    @Before
    fun before() {
        byteArray = ByteArray(1024)
        boundary = "----WebKitFormBoundary" + UUID.randomUUID().toString()
        file = File.createTempFile("prefix", "suffix").apply {
            bufferedWriter().run {
                write("Hello, World")
                flush()
                close()
            }
        }
        outputStream = ByteArrayOutputStream(1024)
    }

    @After
    fun after() {
        file.delete()
    }

    @Test
    fun multipartFormFile() {
        val multipartFile = MultipartFormFile(
            name = "fieldName",
            fileName = "textFile.txt",
            contentType = "text/plain",
            file = file)
        MultipartFormContent(boundary).apply {
            formFields.add(multipartFile)
            writeToStream(outputStream)
        }
        val actual = String(outputStream.toByteArray())
        val excepted = StringBuilder()
            .append("--$boundary")
            .append(System.lineSeparator())
            .append("Content-Disposition: form-data; name=\"fieldName\"; filename=\"textFile.txt\"")
            .append(System.lineSeparator())
            .append("Content-Type: text/plain")
            .append(System.lineSeparator())
            .append(System.lineSeparator())
            .append("Hello, World")
            .append(System.lineSeparator())
            .append("--$boundary--")
            .toString()
        println(actual)
        Assert.assertEquals(excepted, actual)
    }
}