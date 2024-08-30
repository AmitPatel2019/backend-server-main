package com.asdsoft.mavala.service;

import com.asdsoft.mavala.entity.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

@Service
public class RazorPayService {
    @Autowired
    private RazorpayClient razorpayClient;

    private final String baseUrl = "https://api.razorpay.com/v1/";
    @Value("${razor_pay_key}")
    private String razor_pay_key;
    @Value("${razor_pay_sec}")
    private String razor_pay_sec;

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

    public String getPaymentsByOrderId(String orderId) {
        String url = baseUrl + "orders/" + orderId + "/payments";
        RestTemplate restTemplate = new RestTemplate();

        // Create headers with Basic Auth
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(razor_pay_key, razor_pay_sec);

        // Create an HttpEntity with the headers
        HttpEntity<String> request = new HttpEntity<>(headers);

        // Make the GET request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);

        // Return the response body (JSON string)
        return response.getBody();
    }
    public String getPaymentDetailsByPaymentId(String paymentId) {
        String url = baseUrl + "payments/" + paymentId;
        RestTemplate restTemplate = new RestTemplate();

        // Create headers with Basic Auth
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(razor_pay_key, razor_pay_sec);

        // Create an HttpEntity with the headers
        HttpEntity<String> request = new HttpEntity<>(headers);

        // Make the GET request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, request, String.class);
        if (response.getBody() ==null){
            return null;
        }
        JSONObject jsonResponse = new JSONObject(response.getBody());

        return jsonResponse.getString("status");
    }


    public  boolean verifySignature(String orderId, String paymentId) {
        try {
            String payload = orderId + "|" + paymentId;

            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(razor_pay_sec.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKeySpec);

            byte[] hash = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            String generatedSignature = Base64.getEncoder().encodeToString(hash);

            return generatedSignature.equals(razor_pay_key);
        } catch (Exception e) {
            throw new RuntimeException("Error while verifying Razorpay signature", e);
        }
    }


}
