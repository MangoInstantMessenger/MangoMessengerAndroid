package mangomessenger.core.apis.factories

import mangomessenger.core.apis.CommunitiesApi
import mangomessenger.core.apis.SessionsApi

interface MangoApisFactory {

    fun createCommunityApi() : CommunitiesApi

    fun createSessionsApi() : SessionsApi
}