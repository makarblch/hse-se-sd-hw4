package hw4.auth.authentication.auth

import hw4.auth.authentication.dto.request.LoginUserRequest
import hw4.auth.authentication.dto.request.UserRequest
import hw4.auth.authentication.dto.response.UserResponse
import hw4.auth.authentication.entity.Session
import hw4.auth.authentication.entity.User
import hw4.auth.authentication.func.isValidEmail
import hw4.auth.authentication.func.isValidPassword
import hw4.auth.authentication.jwt.JwtService
import hw4.auth.authentication.repository.UserRepository
import hw4.auth.authentication.service.SessionService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthService (private val userRepository: UserRepository, private val passwordEncoder: PasswordEncoder,
private val jwtService: JwtService, private val authenticationManager: AuthenticationManager, private val sessionService: SessionService) {

    fun addNewUser(userRequest: UserRequest): UserResponse {
        val nickname = userRequest.nickname
        val email = userRequest.email
        // Empty email or nickname
        if (nickname.isEmpty() || email.isEmpty()) {
            return UserResponse(403, "Nickname and email can't be empty.")
        }
        // User already exists
        val usr = userRepository.findAll().find { it.email == email }
        if (usr != null) {
            return UserResponse(403, "Such user already exists.")
        }
        // Incorrect email
        if (!isValidEmail(email)) {
            return UserResponse(403, "Incorrect email.")
        }
        // Incorrect password
        if (!isValidPassword(userRequest.password)) {
            return UserResponse(403, "Incorrect password. Check the requirements.")
        }
        val encryptedPassword = passwordEncoder.encode(userRequest.password)
        val user = User(nickname = nickname, email = email, password = encryptedPassword)
        val id = userRepository.save(user).id
        val token = jwtService.generateToken(user)
        sessionService.saveSession(Session(user_id = id, token = token))
        return UserResponse(200, "Successfully signed up!", token = token)
    }

    fun logInUser(loginUserRequest: LoginUserRequest): UserResponse {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    loginUserRequest.email,
                    loginUserRequest.password
                )
            )
        } catch (_: Exception) {
            return UserResponse(403, "Incorrect login or password.")
        }
        val user = userRepository.findByEmail(loginUserRequest.email) ?: return UserResponse(403, "No such user. Please, sign up.")
        val token = sessionService.findByUserId(user.id)
        if (token != null && token.expires > LocalDateTime.now()) return UserResponse(
            200,
            "Successfully logged in!",
            token = token.token
        )
        val tk = jwtService.generateToken(user)
        sessionService.saveSession(Session(user_id = user.id, token = tk))
        return UserResponse(200, "Successfully logged in!", token = tk)
    }
}