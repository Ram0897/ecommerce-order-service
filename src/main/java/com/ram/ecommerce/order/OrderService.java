package com.ram.ecommerce.order;

import com.ram.ecommerce.cart.Cart;
import com.ram.ecommerce.cart.CartService;
import com.ram.ecommerce.cart.CartItem;
import com.ram.ecommerce.product.Product;
import com.ram.ecommerce.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
 private final OrderRepository orders; private final CartService carts; private final ProductRepository products;
 public OrderService(OrderRepository orders,CartService carts,ProductRepository products){this.orders=orders;this.carts=carts;this.products=products;}
 @Transactional
 public Order create(String userId,String idempotencyKey){
  var existing=orders.findByIdempotencyKey(idempotencyKey); if(existing.isPresent()) return existing.get();
  Cart cart=carts.get(userId); if(cart.getItems().isEmpty()) throw new IllegalStateException("Cart is empty");
  Order order=new Order(userId,idempotencyKey);
  for(CartItem ci:cart.getItems()){
   Product p=products.findById(ci.getProduct().getId()).orElseThrow();
   if(p.getInventoryQuantity()<ci.getQuantity()) throw new IllegalStateException("Insufficient inventory for "+p.getSku());
   p.adjustInventory(-ci.getQuantity());
   OrderItem item=new OrderItem(); item.setProduct(p); item.setQuantity(ci.getQuantity()); item.setUnitPrice(p.getPrice()); order.addItem(item);
  }
  order.calculateTotal(); Order saved=orders.save(order); carts.clear(cart); return saved;
 }
 @Transactional public Order transition(Long id,OrderStatus status){Order o=orders.findById(id).orElseThrow();o.transition(status);return o;}
 @Transactional(readOnly=true) public Order get(Long id){return orders.findById(id).orElseThrow();}
}
