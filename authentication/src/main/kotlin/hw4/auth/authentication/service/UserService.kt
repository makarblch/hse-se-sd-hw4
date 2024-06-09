package hw4.auth.authentication.service

import hw4.auth.authentication.dto.request.LoginUserRequest
import hw4.auth.authentication.dto.request.TokenRequest
import hw4.auth.authentication.dto.request.UserRequest
import hw4.auth.authentication.dto.response.UserDataResponse
import hw4.auth.authentication.dto.response.UserResponse
import hw4.auth.authentication.entity.Session
import hw4.auth.authentication.entity.User
import hw4.auth.authentication.func.Encryptor
import hw4.auth.authentication.func.isValidEmail
import hw4.auth.authentication.func.isValidPassword
import hw4.auth.authentication.jwt.JwtService
import hw4.auth.authentication.repository.UserRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class UserService(
    val userRepository: UserRepository
) : UserDetailsService {


    fun getInfo(token: TokenRequest): UserDataResponse {
//        val session: Session = sessionService.getSession(token.token)
//            ?: return UserDataResponse(403, "Failed. Not enough rights.")
//        if (session.expires <= LocalDateTime.now()) {
//            return UserDataResponse(403, "Your session has expired. Please, log in to continue.")
//        }
        val name = SecurityContextHolder.getContext().authentication.name
        val user = userRepository.findAll().find { it.email == name }!!
        return UserDataResponse(200, "Successfully!", user.id, user.nickname, user.email)
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