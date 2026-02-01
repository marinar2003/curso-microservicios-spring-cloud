package com.example.order_service.controller;

import com.example.order_service.client.InventoryClient;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order") // Es mejor darle una ruta base
public class OrderController {

    @Autowired
    private InventoryClient inventoryClient;

    @GetMapping("/create")
// El nombre del método fallback debe coincidir letra por letra
    @CircuitBreaker(name = "inventoryCB", fallbackMethod = "fallbackInventory")
    public String createOrder(@RequestParam("code") String code) {
        return inventoryClient.isInStock(code);
    }

    // IMPORTANTE: Los parámetros deben ser (ParámetrosOriginales, Throwable t)
    public String fallbackInventory(String code, Throwable t) {
        return "Fallback: El servicio de inventario no está disponible actualmente para " + code;
    }
}