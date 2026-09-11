package com.ram.ecommerce.order;

import com.ram.ecommerce.product.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) private Order order;
    @ManyToOne(fetch = FetchType.LAZY, optional = false) private Product product;
    private int quantity;
    private BigDecimal unitPrice;
    public Long getId(){return id;}
    public Order getOrder(){return order;}
    public void setOrder(Order value){order=value;}
    public Product getProduct(){return product;}
    public void setProduct(Product value){product=value;}
    public int getQuantity(){return quantity;}
    public void setQuantity(int value){quantity=value;}
    public BigDecimal getUnitPrice(){return unitPrice;}
    public void setUnitPrice(BigDecimal value){unitPrice=value;}
    public BigDecimal subtotal(){return unitPrice.multiply(BigDecimal.valueOf(quantity));}
}
