package mangomessenger.bisunesslogic.data

import mangomessenger.core.types.models.Tokens

class TokenStorageImpl : TokenStorage {
    private var tokens: Tokens = Tokens()

    override fun getTokens(): Tokens {
        return tokens
    }

    override fun updateTokens(tokens: Tokens) {
        this.tokens = tokens
    }
}