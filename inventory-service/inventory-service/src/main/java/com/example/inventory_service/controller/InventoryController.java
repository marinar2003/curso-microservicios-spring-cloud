package com.example.inventory_service.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Value("${server.port}")
    private String port; // Spring llenará esto con 8081 o 9090 automáticamente

    @GetMapping
    public String isInStock(@RequestParam("code") String code) {
        // Si port es null por algún error de inyección, esto daría error.
        return "Stock OK para: " + code + " (Respondido por el puerto: " + port + ")";
    }
}

