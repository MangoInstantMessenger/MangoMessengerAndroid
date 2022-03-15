package mangomessenger.http

import java.net.HttpURLConnection

data class HttpResponse(val connection: HttpURLConnection, val data: ByteArray) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        if (!data.contentEquals((other as HttpResponse).data)) return false

        return true
    }

    override fun hashCode(): Int {
        return data.contentHashCode()
    }
}
