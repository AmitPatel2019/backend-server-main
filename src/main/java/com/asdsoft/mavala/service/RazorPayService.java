package com.asdsoft.mavala.service;

import com.asdsoft.mavala.entity.Order;
import com.razorpay.Payment;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    public com.razorpay.Payment checkPaymentStatus(String paymentId) throws RazorpayException {
        return razorpayClient.payments.fetch(paymentId);
    }
     private JSONObject getOrderDetails(Order order) {
        JSONObject orderRequest = new JSONObject();
        orderRequest.put("amount", order.getAmount());
        orderRequest.put("currency", "INR");
        orderRequest.put("payment_capture", 1);
        orderRequest.put("receipt", order.getReceiptId());
        return orderRequest;
    }

    private int couponCheck(String coupon) {
        if (coupon.equalsIgnoreCase("TESTCOUPON")) {
            return 199;
        }
        return 299;
    }
/*    public List<String> getSuccessfulPaymentIdsByOrderId(String orderId) {
        List<String> successfulPayments = new ArrayList<>();
        try {
            // Create a JSONObject and add orderId to it
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("order_id", "order_OqL8LxaHa108pU");
           // Payment  payments1= razorpayClient.payments.fetch("pay_OqL8cbARSFGdS1");
            // Fetch all payments associated with this order
            List<Payment> payments = razorpayClient.payments.fetchAll(jsonObject);

            // Check each payment's status and add only successful ones
            for (Payment payment : payments) {
                String paymentId = payment.get("id");
                String paymentStatus = payment.get("status");

                // If payment status is 'captured', consider it a success and add to the list
                if ("captured".equalsIgnoreCase(paymentStatus)) {
                    successfulPayments.add("Payment ID: " + paymentId + " Status: Success");
                }
            }

        } catch (Exception e) {
           // successfulPayments.add("Error fetching payment statuses.");
        }
        return successfulPayments;
    }*/

}
