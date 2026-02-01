package Controller;

import client.InventoryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order") // Es mejor darle una ruta base
public class OrderController {

    @Autowired
    private InventoryClient inventoryClient;

    @GetMapping("/create")
    public String createOrder() {
        // Llamamos a la INSTANCIA (inventoryClient) y al metodo
        String status = inventoryClient.isInStock("CODIGO_PRUEBA");
        return "Pedido recibido. Estado del inventario: " + status;
    }
}