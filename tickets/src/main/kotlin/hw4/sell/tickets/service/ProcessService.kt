package hw4.sell.tickets.service

import org.springframework.stereotype.Service
import hw4.sell.tickets.repository.OrderRepository
import kotlin.random.Random

@Service
class ProcessService(var orderRepository: OrderRepository) {
    private lateinit var thread : Thread

    fun startProcessing() {
        thread = Thread { threadFunc() }
        thread.start()
    }

    fun stopProcessing() {
        try {
            thread.interrupt()
        } catch (ex : Exception) {
            println(ex.message)
        }
    }

    private fun threadFunc() {
        while (true) {
            try {
                val getLst = orderRepository.findOrderByStatus(1) ?: continue
                val randomOrder = getLst.random()

                val id = (0..10).random()
                if (id % 2 == 0) {
                    randomOrder.status = 2
                } else {
                    randomOrder.status = 3
                }
                orderRepository.save(randomOrder)
            } catch (ex : Exception) {
                println("All orders have status different from 1")
            }

            Thread.sleep(10000)
        }
    }
}