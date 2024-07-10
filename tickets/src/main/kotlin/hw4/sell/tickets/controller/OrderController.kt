package hw4.sell.tickets.controller

import hw4.sell.tickets.dto.request.BuyRequest
import hw4.sell.tickets.dto.request.OrderRequest
import hw4.sell.tickets.dto.response.BuyResponse
import hw4.sell.tickets.dto.response.OrderResponse
import hw4.sell.tickets.service.OrderService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/order")
class OrderController(val orderService: OrderService) {
    @PostMapping("/take")
    fun take(@RequestBody buyRequest: BuyRequest, @RequestHeader("Authorization") token : String) : ResponseEntity<BuyResponse> {
        val resp : BuyResponse = orderService.takeOrder(buyRequest, token)
        return ResponseEntity.status(resp.code).body(resp)
    }

    @PostMapping("/status")
    fun checkStatus(@RequestBody orderRequest: OrderRequest, @RequestHeader("Authorization") token : String) : ResponseEntity<OrderResponse> {
        val resp : OrderResponse = orderService.getStatus(orderRequest, token)
        return ResponseEntity.status(resp.code).body(resp)
    }
}