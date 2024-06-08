package hw4.auth.authentication.service

import hw4.auth.authentication.dto.request.UserRequest
import hw4.auth.authentication.dto.response.SignUpResponse
import hw4.auth.authentication.entity.Session
import hw4.auth.authentication.entity.User
import hw4.auth.authentication.func.Encryptor
import hw4.auth.authentication.func.isValidEmail
import hw4.auth.authentication.func.isValidPassword
import hw4.auth.authentication.jwt.JwtService
import hw4.auth.authentication.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository, val jwtService: JwtService, val sessionService: SessionService) {

    fun addNewUser(userRequest: UserRequest): SignUpResponse {
        val nickname = userRequest.nickname
        val email = userRequest.email
        // Empty email or nickname
        if (nickname.isEmpty() || email.isEmpty()) {
            return SignUpResponse(403, "Nickname and email can't be empty.")
        }
        // User already exists
        userRepository.findUser(nickname, email)
            ?: return SignUpResponse(403, "Such user already exists.")
        // Incorrect email
        if (!isValidEmail(email)) {
            return SignUpResponse(403, "Incorrect email.")
        }
        // Incorrect password
        if (!isValidPassword(userRequest.password)) {
            return SignUpResponse(403, "Incorrect password. Check the requirements.")
        }
        val encryptedPassword = Encryptor.encryptPassword(userRequest.password)
        val user = User(nickname = nickname, email = email, password = encryptedPassword)
        val id = userRepository.save(user).id
        val token = jwtService.generateToken(user)
        sessionService.saveSession(Session(user_id = id, token = token))
        return SignUpResponse(200, "Successfully signed up!", token = token)
    }
}