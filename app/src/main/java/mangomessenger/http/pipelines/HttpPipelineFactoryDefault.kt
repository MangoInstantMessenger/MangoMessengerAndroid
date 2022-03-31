package mangomessenger.http.pipelines

import mangomessenger.http.HttpClient

class HttpPipelineFactoryDefault : HttpPipelineFactory {
    override fun createHttpPipeline(): HttpPipeline {
        val httpClient = HttpClient()
        val httpHandler = HttpHandler(httpClient)
        return HttpPipeline(httpHandler)
    }
}