package com.example.food;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class FoodController {

```
// Food Menu API
@GetMapping("/menu")
public List<Map<String, Object>> getMenu() {

    List<Map<String, Object>> menu = new ArrayList<>();

    menu.add(createFoodItem(1, "Pizza", 250, "Italian", "Pizza Hut"));
    menu.add(createFoodItem(2, "Burger", 150, "Fast Food", "Burger King"));
    menu.add(createFoodItem(3, "Biryani", 200, "Indian", "Biryani House"));
    menu.add(createFoodItem(4, "Momos", 120, "Chinese", "Wow Momos"));
    menu.add(createFoodItem(5, "Pasta", 220, "Italian", "Dominos"));
    menu.add(createFoodItem(6, "Fried Rice", 180, "Chinese", "Mainland China"));
    menu.add(createFoodItem(7, "Paneer Tikka", 240, "Indian", "Barbeque Nation"));
    menu.add(createFoodItem(8, "Chicken Roll", 140, "Street Food", "Roll Express"));

    return menu;
}

// Place Order API
@PostMapping("/order")
public Map<String, Object> orderFood(
        @RequestParam String customerName,
        @RequestParam String item,
        @RequestParam int quantity,
        @RequestParam String address) {

    int pricePerItem = getPrice(item);

    int totalAmount = quantity * pricePerItem;

    Map<String, Object> response = new HashMap<>();

    response.put("message", "Order Placed Successfully");
    response.put("customerName", customerName);
    response.put("foodItem", item);
    response.put("quantity", quantity);
    response.put("pricePerItem", pricePerItem);
    response.put("totalAmount", totalAmount);
    response.put("deliveryAddress", address);
    response.put("estimatedDeliveryTime", "30 Minutes");
    response.put("orderStatus", "Preparing");
    response.put("orderId", UUID.randomUUID().toString());

    return response;
}

// Order Status API
@GetMapping("/status/{orderId}")
public Map<String, String> getOrderStatus(
        @PathVariable String orderId) {

    Map<String, String> status = new HashMap<>();

    status.put("orderId", orderId);
    status.put("status", "Out For Delivery");

    return status;
}

// Helper Method
private Map<String, Object> createFoodItem(
        int id,
        String name,
        int price,
        String category,
        String restaurant) {

    Map<String, Object> item = new HashMap<>();

    item.put("id", id);
    item.put("name", name);
    item.put("price", price);
    item.put("category", category);
    item.put("restaurant", restaurant);

    return item;
}

// Price Calculator
private int getPrice(String item) {

    return switch (item.toLowerCase()) {

        case "pizza" -> 250;
        case "burger" -> 150;
        case "biryani" -> 200;
        case "momos" -> 120;
        case "pasta" -> 220;
        case "fried rice" -> 180;
        case "paneer tikka" -> 240;
        case "chicken roll" -> 140;

        default -> 100;
    };
}
```

}
