package mangomessenger.bisunesslogic.data

import mangomessenger.core.types.models.Tokens

interface TokensRepository {

    fun getTokens(): Tokens
    
    fun updateTokens(tokens: Tokens)
}