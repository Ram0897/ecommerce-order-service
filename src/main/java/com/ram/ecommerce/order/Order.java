package com.ram.ecommerce.order;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

@Entity @Table(name="orders",indexes=@Index(name="idx_orders_user",columnList="user_id"))
public class Order {
 @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
 @Column(name="user_id",nullable=false) private String userId;
 @Column(name="idempotency_key",nullable=false,unique=true,length=100) private String idempotencyKey;
 @Enumerated(EnumType.STRING) @Column(nullable=false,length=20) private OrderStatus status=OrderStatus.CREATED;
 @Column(nullable=false,precision=19,scale=2) private BigDecimal totalAmount;
 @Column(nullable=false,updatable=false) private Instant createdAt;
 @OneToMany(mappedBy="order",cascade=CascadeType.ALL,orphanRemoval=true) private List<OrderItem> items=new ArrayList<>();
 protected Order(){}
 public Order(String userId,String key){this.userId=userId;this.idempotencyKey=key;this.createdAt=Instant.now();}
 public Long getId(){return id;} public String getUserId(){return userId;} public String getIdempotencyKey(){return idempotencyKey;} public OrderStatus getStatus(){return status;} public BigDecimal getTotalAmount(){return totalAmount;} public List<OrderItem> getItems(){return items;}
 public void addItem(OrderItem item){item.setOrder(this);items.add(item);}
 public void calculateTotal(){totalAmount=items.stream().map(OrderItem::subtotal).reduce(BigDecimal.ZERO,BigDecimal::add);}
 public void transition(OrderStatus next){if(status==OrderStatus.CANCELLED||status==OrderStatus.DELIVERED)throw new IllegalStateException("Order cannot transition from "+status); status=next;}
}
