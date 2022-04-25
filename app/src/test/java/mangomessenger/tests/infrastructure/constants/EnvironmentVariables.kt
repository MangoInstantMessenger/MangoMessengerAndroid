package mangomessenger.tests.infrastructure.constants

sealed class EnvironmentVariables {
    companion object {
        fun testEmail() = System.getenv("TESTS_MANGO_EMAIL").orEmpty()

        fun testPassword() = System.getenv("TESTS_MANGO_PASSWORD").orEmpty()

        fun mangoDomain() = System.getenv("MANGO_DOMAIN").orEmpty()

        fun testImageFilePath() = System.getenv("TESTS_MANGO_IMAGE").orEmpty()

        fun testNewPassword() = System.getenv("TEST_MANGO_NEW_PASSWORD").orEmpty()
    }
}