package com.example.food;

import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
public class FoodController {

    @GetMapping("/api/menu")
    public String menu() {

        return """
                ===== FOOD MENU =====

                Pizza   : ₹250
                Burger  : ₹150
                Biryani : ₹200
                Momos   : ₹120

                =====================
                """;
    }

    @PostMapping("/api/order")
    public String orderFood(
            @RequestParam String item,
            @RequestParam int quantity) {

        return "Order Confirmed : "
                + quantity
                + " "
                + item;
    }
}
