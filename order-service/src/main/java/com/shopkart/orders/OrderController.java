package com.shopkart.orders;
import java.time.Instant;import java.util.*;import java.util.concurrent.CopyOnWriteArrayList;import java.util.concurrent.atomic.AtomicLong;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/orders") @CrossOrigin
public class OrderController {
 private final AtomicLong sequence=new AtomicLong(1000);private final List<Map<String,Object>> orders=new CopyOnWriteArrayList<>();
 @PostMapping public Map<String,Object> create(@RequestBody Map<String,Object> request){Object items=request.get("items");if(items==null)throw new IllegalArgumentException("Order items are required");Map<String,Object> order=new LinkedHashMap<>();order.put("orderId","SK-"+sequence.incrementAndGet());order.put("createdAt",Instant.now().toString());order.put("status","CREATED");order.put("items",items);orders.add(order);return order;}
 @GetMapping public List<Map<String,Object>> all(){return List.copyOf(orders);}
 @GetMapping("/health") public Map<String,String> health(){return Map.of("service","order-service","status","UP");}
}
