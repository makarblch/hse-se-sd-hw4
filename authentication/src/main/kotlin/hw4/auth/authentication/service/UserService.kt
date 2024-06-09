package hw4.auth.authentication.service

import hw4.auth.authentication.dto.request.LoginUserRequest
import hw4.auth.authentication.dto.request.TokenRequest
import hw4.auth.authentication.dto.request.UserRequest
import hw4.auth.authentication.dto.response.UserResponse
import hw4.auth.authentication.entity.Session
import hw4.auth.authentication.entity.User
import hw4.auth.authentication.func.Encryptor
import hw4.auth.authentication.func.isValidEmail
import hw4.auth.authentication.func.isValidPassword
import hw4.auth.authentication.jwt.JwtService
import hw4.auth.authentication.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(val userRepository: UserRepository, val jwtService: JwtService, val sessionService: SessionService) {

    fun addNewUser(userRequest: UserRequest): UserResponse {
        val nickname = userRequest.nickname
        val email = userRequest.email
        // Empty email or nickname
        if (nickname.isEmpty() || email.isEmpty()) {
            return UserResponse(403, "Nickname and email can't be empty.")
        }
        // User already exists
        val usr = userRepository.findAll().find { it.email == email || it.nickname == nickname }
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
        val encryptedPassword = Encryptor.encryptPassword(userRequest.password)
        val user = User(nickname = nickname, email = email, password = encryptedPassword)
        val id = userRepository.save(user).id
        val token = jwtService.generateToken(user)
        sessionService.saveSession(Session(user_id = id, token = token))
        return UserResponse(200, "Successfully signed up!", token = token)
    }

    fun logInUser(loginUserRequest: LoginUserRequest): UserResponse {
        val password = Encryptor.encryptPassword(loginUserRequest.password)
        val email = loginUserRequest.email
        val user = userRepository.findByEmail(email) ?: return UserResponse(403, "No such user. Please, sign up.")
        if (password != user.password) {
            return UserResponse(403, "Incorrect password.")
        }
        // If there is a vaild token for this user, return it
        val token = sessionService.findByUserId(user.id)
        if (token != null && token.expires > LocalDateTime.now()) return UserResponse(200, "Successfully logged in!", token = token.token)
        val tk = jwtService.generateToken(user)
        sessionService.saveSession(Session(user_id = user.id, token = tk))
        return UserResponse(200, "Successfully logged in!", token = tk)
    }

    fun getInfo(token: TokenRequest): UserResponse {
        val session: Session = sessionService.getSession(token.token)
            ?: return UserResponse(403, "Please, log in to continue.")
        if (session.expires <= LocalDateTime.now()) {
            return UserResponse(403, "Your session has expired. Please, log in to continue.")
        }
        val user = userRepository.findAll().find { it.id == session.user_id }
        return UserResponse(200, "nickname : ${user!!.nickname} ; email : ${user.email}")
    }
}