package hw4.auth.authentication.controller

import hw4.auth.authentication.dto.request.UserRequest
import hw4.auth.authentication.dto.response.UserResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/user")
class UserController {

    @GetMapping("/signup")
    fun signUp(userRequest: UserRequest) : UserResponse {
        // TODO
        return UserResponse()
    }

    @GetMapping("/login")
    fun logIn(userRequest: UserRequest) : UserResponse {
        // TODO
        return UserResponse()
    }

    @GetMapping("/info")
    fun getInfo(token : String) : UserResponse {
        // TODO
        return UserResponse()
    }
}