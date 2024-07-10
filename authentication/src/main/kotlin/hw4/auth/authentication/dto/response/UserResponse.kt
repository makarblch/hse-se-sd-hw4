package hw4.auth.authentication.dto.response

data class UserResponse (
    val status : Int,
    val message : String? = null,
    val token : String? = null
)