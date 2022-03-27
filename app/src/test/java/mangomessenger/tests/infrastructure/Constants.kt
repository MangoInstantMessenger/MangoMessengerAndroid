package mangomessenger.tests.infrastructure

sealed class Constants {
    companion object {
        fun testEmail() = System.getenv("TESTS_MANGO_EMAIL").orEmpty()

        fun testPassword() = System.getenv("TESTS_MANGO_PASSWORD").orEmpty()

        fun mangoDomain() = System.getenv("MANGO_DOMAIN").orEmpty()
    }
}