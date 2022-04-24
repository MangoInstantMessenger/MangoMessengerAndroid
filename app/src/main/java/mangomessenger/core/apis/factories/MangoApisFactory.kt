package mangomessenger.core.apis.factories

import mangomessenger.core.apis.CommunitiesApi
import mangomessenger.core.apis.SessionsApi
import mangomessenger.core.apis.UsersApi

interface MangoApisFactory {

    fun createCommunityApi() : CommunitiesApi

    fun createSessionsApi() : SessionsApi

    fun createUsersApi() : UsersApi
}