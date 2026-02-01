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

    //@GetMapping("/create")
    //public String createOrder() {
    // Llamamos a la INSTANCIA (inventoryClient) y al metodo
    //String status = inventoryClient.isInStock("CODIGO_PRUEBA");
    //return "Pedido recibido. Estado del inventario: " + status;

    @GetMapping("/create")
    @CircuitBreaker(name = "inventoryCB", fallbackMethod = "fallbackInventory")
    public String createOrder(@RequestParam("code") String code) { // Agregamos el RequestParam
        // Ahora usamos el 'code' que viene de Postman
        String status = inventoryClient.isInStock(code);
        return "Pedido recibido. Producto: " + code + ". Estado del inventario: " + status;
    }

    //Este método se ejecuta cuando el circuito está ABIERTO o hay error
    public String fallbackInventory(String code, Throwable throwable) {
        return "Lo sentimos, el servicio de inventario no está disponible para el producto: " + code + ". Intente más tarde.";
    }
}