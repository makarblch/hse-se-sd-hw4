package hw4.auth.authentication.dto.response

data class UserDataResponse (
    val status : Int,
    val message : String,
    val id : Long = 0,
    val nickname : String? = null,
    val email : String? = null
)