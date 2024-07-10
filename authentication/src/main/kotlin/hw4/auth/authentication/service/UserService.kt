package hw4.auth.authentication.service

import hw4.auth.authentication.dto.request.TokenRequest
import hw4.auth.authentication.dto.response.UserDataResponse
import hw4.auth.authentication.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class UserService(
    val userRepository: UserRepository,
    val sessionService: SessionService
) : UserDetailsService {

    fun getInfo(tokenRequest: TokenRequest): UserDataResponse {
        val session = sessionService.findByToken(token = tokenRequest.token)
        if (session != null && session.expires >= LocalDateTime.now()) {
            val user = userRepository.findById(session.user_id)
            if (user.isEmpty) {
                return UserDataResponse(403, "No such user.")
            }
            return UserDataResponse(200, "Successfully", user.get().id, user.get().nickname, user.get().email)
        }
        return UserDataResponse(403, "Failed to verify token. Try to sign in.")
    }

    override fun loadUserByUsername(email: String?): UserDetails {
        if (email == null) {
            throw UsernameNotFoundException("There is no such user")
        }
        val user = userRepository.findByEmail(email) ?: throw UsernameNotFoundException("There is no such user")

        return org.springframework.security.core.userdetails.User(
            user.email,
            user.password,
            Collections.emptyList()
        )
    }
}