package mangomessenger.bisunesslogic.data

class TokenStorageProvider {
    companion object {
        private var tokenStorage: TokenStorage? = null

        fun getTokenStorage(): TokenStorage {
            if (tokenStorage == null) {
                tokenStorage = TokenStorageImpl()
            }

            return tokenStorage!!
        }
    }
}