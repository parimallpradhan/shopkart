package com.shopkart.gateway;
import java.util.*;import org.springframework.beans.factory.annotation.Value;import org.springframework.http.MediaType;import org.springframework.web.bind.annotation.*;import org.springframework.web.client.RestClient;
@RestController @RequestMapping("/api")
public class ApiGatewayController {
 private final RestClient client; private final String productsUrl,cartUrl,ordersUrl;
 public ApiGatewayController(RestClient.Builder builder,@Value("${services.products-url:http://localhost:8081}") String p,@Value("${services.cart-url:http://localhost:8082}") String c,@Value("${services.orders-url:http://localhost:8083}") String o){client=builder.build();productsUrl=p;cartUrl=c;ordersUrl=o;}
 @GetMapping("/products") public Object products(@RequestParam(required=false) String search){return client.get().uri(productsUrl+"/api/products"+(search==null?"":"?search="+java.net.URLEncoder.encode(search,java.nio.charset.StandardCharsets.UTF_8))).retrieve().body(Object.class);}
 @GetMapping("/products/{id}") public Object product(@PathVariable int id){return client.get().uri(productsUrl+"/api/products/"+id).retrieve().body(Object.class);}
 @GetMapping("/cart") public Object cart(){return client.get().uri(cartUrl+"/api/cart").retrieve().body(Object.class);}
 @PostMapping(value="/cart",consumes=MediaType.APPLICATION_JSON_VALUE) public Object addCart(@RequestBody Map<String,Integer> body){return client.post().uri(cartUrl+"/api/cart").contentType(MediaType.APPLICATION_JSON).body(body).retrieve().body(Object.class);}
 @DeleteMapping("/cart") public Object clearCart(){return client.delete().uri(cartUrl+"/api/cart").retrieve().body(Object.class);}
 @DeleteMapping("/cart/{id}") public Object removeCart(@PathVariable int id){return client.delete().uri(cartUrl+"/api/cart/"+id).retrieve().body(Object.class);}
 @PostMapping(value="/orders",consumes=MediaType.APPLICATION_JSON_VALUE) public Object order(@RequestBody Map<String,Object> body){return client.post().uri(ordersUrl+"/api/orders").contentType(MediaType.APPLICATION_JSON).body(body).retrieve().body(Object.class);}
 @GetMapping("/orders") public Object orders(){return client.get().uri(ordersUrl+"/api/orders").retrieve().body(Object.class);}
 @GetMapping("/health") public Map<String,String> health(){return Map.of("gateway","UP","productsUrl",productsUrl,"cartUrl",cartUrl,"ordersUrl",ordersUrl);}
}
