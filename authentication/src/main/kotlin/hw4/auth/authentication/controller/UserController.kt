package hw4.auth.authentication.controller

import hw4.auth.authentication.dto.request.LoginUserRequest
import hw4.auth.authentication.dto.request.TokenRequest
import hw4.auth.authentication.dto.request.UserRequest
import hw4.auth.authentication.dto.response.UserResponse
import hw4.auth.authentication.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(val userService: UserService) {

    @PostMapping("/signup")
    fun signUp(@RequestBody userRequest: UserRequest) : ResponseEntity<UserResponse> {
        val resp : UserResponse = userService.addNewUser(userRequest)
        return ResponseEntity.status(resp.status).body(resp)
    }

    @PostMapping("/login")
    fun logIn(@RequestBody userRequest: LoginUserRequest) : ResponseEntity<UserResponse> {
        val resp : UserResponse = userService.logInUser(userRequest)
        return ResponseEntity.status(resp.status).body(resp)
    }

    @PostMapping("/info")
    fun getInfo(@RequestBody token : TokenRequest) : ResponseEntity<UserResponse> {
        val resp : UserResponse = userService.getInfo(token)
        return ResponseEntity.status(resp.status).body(resp)
    }
}