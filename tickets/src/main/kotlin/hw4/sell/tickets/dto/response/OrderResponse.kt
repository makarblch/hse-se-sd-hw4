package hw4.sell.tickets.dto.response

data class OrderResponse (
    val code : Int,
    val status : Int?,
    val message : String?
)