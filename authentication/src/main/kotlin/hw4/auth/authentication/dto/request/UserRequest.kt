package hw4.auth.authentication.dto.request

data class UserRequest(
    var nickname: String,
    var email: String,
    var password: String,
)