package com.shopkart.cart;
import java.util.*;import java.util.concurrent.ConcurrentHashMap;import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/cart") @CrossOrigin
public class CartController {
 private final Map<Integer,Integer> items=new ConcurrentHashMap<>();
 @GetMapping public Map<String,Object> get(){return view();}
 @PostMapping public Map<String,Object> add(@RequestBody Map<String,Integer> body){int id=body.getOrDefault("productId",0);int qty=Math.max(1,body.getOrDefault("quantity",1));if(id<1)throw new IllegalArgumentException("productId is required");items.merge(id,qty,Integer::sum);return view();}
 @DeleteMapping("/{productId}") public Map<String,Object> remove(@PathVariable int productId){items.remove(productId);return view();}
 @DeleteMapping public Map<String,Object> clear(){items.clear();return view();}
 private Map<String,Object> view(){List<Map<String,Integer>> list=items.entrySet().stream().map(e->Map.of("productId",e.getKey(),"quantity",e.getValue())).toList();return Map.of("items",list,"totalItems",items.values().stream().mapToInt(Integer::intValue).sum());}
 @GetMapping("/health") public Map<String,String> health(){return Map.of("service","cart-service","status","UP");}
}
