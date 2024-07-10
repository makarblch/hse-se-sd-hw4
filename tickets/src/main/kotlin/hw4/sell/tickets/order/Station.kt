package hw4.sell.tickets.order

import jakarta.persistence.*

@Entity(name="station")
data class Station (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String
)