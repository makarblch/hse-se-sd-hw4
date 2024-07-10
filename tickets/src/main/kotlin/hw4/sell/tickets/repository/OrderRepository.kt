package hw4.sell.tickets.repository

import hw4.sell.tickets.order.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderRepository : JpaRepository<Order, Long> {
    fun findOrderByStatus(status : Int) : List<Order>
}