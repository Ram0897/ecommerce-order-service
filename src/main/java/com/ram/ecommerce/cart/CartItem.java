package com.ram.ecommerce.cart;

import com.ram.ecommerce.product.Product;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="cart_items", uniqueConstraints=@UniqueConstraint(columnNames={"cart_id","product_id"}))
public class CartItem {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private Cart cart;
 @ManyToOne(fetch=FetchType.LAZY,optional=false) private Product product;
 @Column(nullable=false) private int quantity;
 public Long getId(){return id;} public Cart getCart(){return cart;} public void setCart(Cart c){cart=c;}
 public Product getProduct(){return product;} public void setProduct(Product p){product=p;} public int getQuantity(){return quantity;} public void setQuantity(int q){quantity=q;}
 public BigDecimal subtotal(){return product.getPrice().multiply(BigDecimal.valueOf(quantity));}
}
