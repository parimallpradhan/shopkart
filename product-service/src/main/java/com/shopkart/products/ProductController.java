package com.shopkart.products;
import java.util.*;
import org.springframework.web.bind.annotation.*;
@RestController @RequestMapping("/api/products") @CrossOrigin
public class ProductController {
 private final List<Map<String,Object>> products = List.of(
  product(1,"Wireless Headphones","Electronics",2499,"https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=600&q=80"),
  product(2,"Smart Watch","Accessories",3999,"https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=600&q=80"),
  product(3,"Running Shoes","Fashion",1999,"https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=600&q=80"),
  product(4,"Travel Backpack","Accessories",1299,"https://images.unsplash.com/photo-1544816155-12df9643f363?auto=format&fit=crop&w=600&q=80"));
 private static Map<String,Object> product(int id,String name,String category,int price,String image){return Map.of("id",id,"name",name,"category",category,"price",price,"image",image);}
 @GetMapping public List<Map<String,Object>> all(@RequestParam(required=false) String search){if(search==null||search.isBlank())return products;return products.stream().filter(p->p.get("name").toString().toLowerCase().contains(search.toLowerCase())||p.get("category").toString().toLowerCase().contains(search.toLowerCase())).toList();}
 @GetMapping("/{id}") public Map<String,Object> one(@PathVariable int id){return products.stream().filter(p->((int)p.get("id"))==id).findFirst().orElseThrow(()->new NoSuchElementException("Product not found: "+id));}
 @GetMapping("/health") public Map<String,String> health(){return Map.of("service","product-service","status","UP");}
}
