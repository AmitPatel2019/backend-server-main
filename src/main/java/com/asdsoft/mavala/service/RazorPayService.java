package com.asdsoft.mavala.service;

import com.asdsoft.mavala.entity.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RazorPayService {
    @Autowired
    private RazorpayClient razorpayClient;

    public com.razorpay.Order createRazorPayOrder(Order order) throws RazorpayException {
        return razorpayClient.orders.create(getOrderDetails(order));
    }

    public com.razorpay.Order checkOrderStatus(Order order) throws RazorpayException {
        return razorpayClient.orders.fetch(order.getOrderId());
    }

    private JSONObject getOrderDetails(Order order) {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", order.getAmount());
        orderRequest.put("currency", "INR");
        orderRequest.put("receipt", order.getReceiptId());
        return orderRequest;
    }

    private int couponCheck(String coupon) {
        if (coupon.equalsIgnoreCase("TESTCOUPON")) {
            return 199;
        }
        return 299;
    }


}
