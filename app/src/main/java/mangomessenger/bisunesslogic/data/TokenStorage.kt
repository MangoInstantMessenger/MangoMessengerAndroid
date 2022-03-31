package mangomessenger.bisunesslogic.data

import mangomessenger.core.types.models.Tokens

interface TokenStorage {

    fun getTokens(): Tokens
    
    fun updateTokens(tokens: Tokens)
}