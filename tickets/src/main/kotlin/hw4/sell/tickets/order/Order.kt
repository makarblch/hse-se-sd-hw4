package hw4.sell.tickets.order

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name="\"order\"")
data class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(nullable = false)
    var user_id: Long,

    @Column(nullable = false)
    var from_station_id: Long,

    @Column(nullable = false)
    var to_station_id: Long,

    @Column(nullable = false)
    var status: Int = 1,

    @Column(columnDefinition = "TIMESTAMP")
    val created: LocalDateTime = LocalDateTime.now()
)