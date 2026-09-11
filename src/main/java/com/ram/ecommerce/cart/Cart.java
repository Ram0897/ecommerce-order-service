package com.ram.ecommerce.cart;

import com.ram.ecommerce.product.Product;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true) private String userId;
    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();
    @Version private Long version;

    public Long getId(){return id;}
    public String getUserId(){return userId;}
    public void setUserId(String userId){this.userId=userId;}
    public List<CartItem> getItems(){return items;}
    public BigDecimal total(){return items.stream().map(CartItem::subtotal).reduce(BigDecimal.ZERO, BigDecimal::add);}
    public void addItem(Product product,int quantity){
        items.stream().filter(i -> i.getProduct().getId().equals(product.getId())).findFirst()
            .ifPresentOrElse(i -> i.setQuantity(i.getQuantity()+quantity), () -> {
                CartItem item=new CartItem(); item.setCart(this); item.setProduct(product); item.setQuantity(quantity); items.add(item);
            });
    }
}
