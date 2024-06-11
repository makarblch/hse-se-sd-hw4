package hw4.sell.tickets.repository

import hw4.sell.tickets.order.Station
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StationRepository : JpaRepository<Station, Long> {
}