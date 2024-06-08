package hw4.auth.authentication.dto.response

data class SignUpResponse (
    val status : Int,
    val message : String? = null,
    val token : String? = null
)