package hw4.auth.authentication.controller

import hw4.auth.authentication.auth.AuthService
import hw4.auth.authentication.dto.request.LoginUserRequest
import hw4.auth.authentication.dto.request.TokenRequest
import hw4.auth.authentication.dto.request.UserRequest
import hw4.auth.authentication.dto.response.UserDataResponse
import hw4.auth.authentication.dto.response.UserResponse
import hw4.auth.authentication.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/user")
class UserController(val authService: AuthService, val userService: UserService) {

    @PostMapping("/signup")
    fun signUp(@RequestBody userRequest: UserRequest) : ResponseEntity<UserResponse> {
        val resp : UserResponse = authService.addNewUser(userRequest)
        return ResponseEntity.status(resp.status).body(resp)
    }

    @PostMapping("/login")
    fun logIn(@RequestBody userRequest: LoginUserRequest) : ResponseEntity<UserResponse> {
        val resp : UserResponse = authService.logInUser(userRequest)
        return ResponseEntity.status(resp.status).body(resp)
    }

    @PostMapping("/info")
    fun getInfo(@RequestBody token : TokenRequest) : ResponseEntity<UserDataResponse> {
        val resp : UserDataResponse = userService.getInfo(token)
        return ResponseEntity.status(resp.status).body(resp)
    }
}