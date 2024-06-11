package hw4.sell.tickets.service

import hw4.sell.tickets.order.Station
import hw4.sell.tickets.repository.StationRepository
import org.springframework.stereotype.Service

@Service
class StationService(val stationRepository: StationRepository) {
    fun validateStation(id : Long) : Boolean {
        val st = stationRepository.findById(id)
        return st.isPresent
    }

    init {
        stationRepository.save(Station(name="Voronezh"))
        stationRepository.save(Station(name="Egg"))
        stationRepository.save(Station(name="Moscow"))
        stationRepository.save(Station(name= "Perm"))
        stationRepository.save(Station(name="Kaliningrad"))
    }
}