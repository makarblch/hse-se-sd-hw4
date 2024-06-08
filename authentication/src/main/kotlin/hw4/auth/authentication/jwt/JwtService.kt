package hw4.auth.authentication.jwt

import hw4.auth.authentication.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService {
    private val secretKey = "my0320character0ultra0secure0and0ultra0long0secret"

    fun extractAllClaims(token: String): Claims {
        return Jwts.parser().verifyWith(signingKey())
            .build().parseSignedClaims(token).payload
    }

    fun generateToken(user: User): String {
        return Jwts.builder().subject(user.nickname)
            .issuedAt(Date(System.currentTimeMillis()))
            .expiration(Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
            .signWith(signingKey())
            .compact()
    }

    private fun signingKey(): SecretKey {
        val keyBytes = Decoders.BASE64.decode(secretKey)
        return Keys.hmacShaKeyFor(keyBytes)
    }
}