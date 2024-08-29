package com.asdsoft.mavala.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "ORDERS")
public class Order {
    @Id
    private String orderId;
    private String receiptId;
    @ManyToOne
    @JoinColumn(name = "user_firebase_id")
    private UserMawala user;
    private String coupon;
    private String orderStatus = "created";
    private Date created_at;
    private String paymentId;
    int amount;

}
