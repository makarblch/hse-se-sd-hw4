package hw4.sell.tickets.service

import hw4.sell.tickets.dto.request.BuyRequest
import hw4.sell.tickets.dto.request.OrderRequest
import hw4.sell.tickets.dto.request.TokenRequest
import hw4.sell.tickets.dto.response.AuthResponse
import hw4.sell.tickets.dto.response.BuyResponse
import hw4.sell.tickets.dto.response.OrderResponse
import hw4.sell.tickets.order.Order
import hw4.sell.tickets.repository.OrderRepository
import hw4.sell.tickets.repository.StationRepository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.net.http.HttpClient

@Service
class OrderService(val orderRepository: OrderRepository, val stationService: StationService, private val processService: ProcessService) {
    val httpClient = WebClient.create()

    init {
        orderRepository.save(Order(user_id = 1, from_station_id=1, to_station_id=2))
        processService.startProcessing()
    }

    fun sendRequest(token : String) : AuthResponse? {
        return httpClient.post()
            .uri("http://AuthService:8080/user/info")
            .bodyValue(TokenRequest(token = token.substring(7)))
            .retrieve()
            .bodyToMono(AuthResponse::class.java)
            .block()
        // return AuthResponse(200, "ok", 3, "egg", "egg@mail.ru")
    }

    fun takeOrder(buyRequest: BuyRequest, token: String): BuyResponse {
        val req = sendRequest(token) ?: return BuyResponse(401, null, "Failed to authenticate.")
        if (req.status != 200) {
            return BuyResponse(req.status, null, req.message)
        }
        val from = buyRequest.idStart
        val to = buyRequest.idEnd
        print(stationService.validateStation(to))
        if (stationService.validateStation(from) && stationService.validateStation(to)) {
            val order = Order(user_id = req.id!!, from_station_id = from, to_station_id = to)
            val id = orderRepository.save(order).id
            return BuyResponse(200, id, "Successfully bought!")
        }
        return BuyResponse(400, null, "Invalid station id.")
    }

    fun getStatus(orderRequest: OrderRequest, token: String) : OrderResponse {
        val req = sendRequest(token) ?: return OrderResponse(401, status = null, "Failed to authenticate.")
        if (req.status != 200) {
            return OrderResponse(req.status, message = req.message, status = null)
        }
        val order = orderRepository.findById(orderRequest.id)
        if (order.isEmpty) {
            return OrderResponse(400, null, "Invalid order id.")
        }
        if (order.get().user_id != req.id) {
            return OrderResponse(403, null, "This order isn't yours.")
        }
        return OrderResponse(200, order.get().status, "Successfully")
    }



}