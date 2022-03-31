package mangomessenger.http.pipelines

interface HttpPipelineFactory {
    fun createHttpPipeline(): HttpPipeline
}