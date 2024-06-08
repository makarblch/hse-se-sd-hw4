package hw4.auth.authentication.controller

import hw4.auth.authentication.dto.request.UserRequest
import hw4.auth.authentication.dto.response.SignUpResponse
import hw4.auth.authentication.dto.response.UserResponse
import hw4.auth.authentication.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/user")
class UserController(val userService: UserService) {

    @PostMapping("/signup")
    fun signUp(@RequestBody userRequest: UserRequest) : ResponseEntity<SignUpResponse> {
        val resp : SignUpResponse = userService.addNewUser(userRequest)
        return ResponseEntity.status(resp.status).body(resp)
    }

    @PostMapping("/login")
    fun logIn(@RequestBody userRequest: UserRequest) : UserResponse {
        // TODO
        return UserResponse()
    }

    @PostMapping("/info")
    fun getInfo(@RequestBody token : String) : UserResponse {
        // TODO
        return UserResponse()
    }
}