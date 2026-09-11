package com.ram.ecommerce.cart;

import com.ram.ecommerce.common.ResourceNotFoundException;
import com.ram.ecommerce.product.Product;
import com.ram.ecommerce.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {
 private final CartRepository carts; private final ProductRepository products;
 public CartService(CartRepository carts,ProductRepository products){this.carts=carts;this.products=products;}
 @Transactional public Cart addItem(String userId,java.util.UUID productId,int quantity){
  if(quantity<1) throw new IllegalArgumentException("Quantity must be positive");
  Product p=products.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product not found"));
  if(p.getInventoryQuantity()<quantity) throw new IllegalStateException("Insufficient inventory");
  Cart c=carts.findByUserId(userId).orElseGet(()->{Cart n=new Cart();n.setUserId(userId);return n;}); c.addItem(p,quantity); return carts.save(c);
 }
 @Transactional(readOnly=true) public Cart get(String userId){return carts.findByUserId(userId).orElseThrow(()->new ResourceNotFoundException("Cart not found"));}
 @Transactional public void clear(Cart c){c.getItems().clear();carts.save(c);}
}
