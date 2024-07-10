package hw4.sell.tickets.dto.response

data class AuthResponse(
    val status: Int,
    val message: String,
    val id: Long?,
    val nickname: String?,
    val email: String?
)