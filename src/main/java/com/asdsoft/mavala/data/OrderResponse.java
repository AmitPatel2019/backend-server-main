package com.asdsoft.mavala.data;

import com.asdsoft.mavala.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderResponse {
    private String orderId;
    private String receiptId;
    private String orderStatus;
    private int amount;

    public OrderResponse(Order order) {
        this.orderId = order.getOrderId();
        this.receiptId = order.getReceiptId();
        this.orderStatus = order.getOrderStatus();
        this.amount = order.getAmount();
    }
}
