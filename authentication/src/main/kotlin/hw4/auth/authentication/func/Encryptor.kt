package hw4.auth.authentication.func

import java.security.MessageDigest

object Encryptor {
    private fun getHash(inByte: ByteArray): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(inByte)
        return with(StringBuilder()) {
            bytes.forEach { byte -> append(String.format("%02X", byte)) }
            toString().lowercase()
        }
    }

    fun encryptPassword(password: String): String {
        return getHash(password.toByteArray())
    }
}
