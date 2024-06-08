package hw4.auth.authentication.dto.request

data class LoginUserRequest (
    var email: String,
    var password: String,
)