package hw4.sell.tickets.dto.response

data class BuyResponse (
    val code : Int,
    val orderId : Long?,
    val message : String
)