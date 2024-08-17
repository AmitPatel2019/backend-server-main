package com.asdsoft.mavala.service;

import com.asdsoft.mavala.data.Price;
import com.asdsoft.mavala.entity.Order;
import com.asdsoft.mavala.entity.UserMawala;
import com.asdsoft.mavala.repository.CouponRepository;
import com.asdsoft.mavala.repository.OrderRepository;
import com.asdsoft.mavala.repository.UserRepository;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    private final static String ORDER_COMPLETED = "paid";
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RazorPayService razorPayService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CouponRepository couponRepository;


    public Order createOrder(UserMawala principal, String coupon) throws RazorpayException {
        Order order = new Order();

        order.setUser(principal);
        order.setCoupon(coupon);
        order.setReceiptId(UUID.randomUUID().toString());
        order.setAmount(getAmount(coupon) * 100);
        if (order.getAmount() == 0){
            principal.setPremium(true);
            order.setAmount(100);
            userRepository.save(principal);
        }
        com.razorpay.Order razorPayOrder = razorPayService.createRazorPayOrder(order);
        order.setOrderId(razorPayOrder.get("id"));
        order.setOrderStatus(razorPayOrder.get("status"));
        order.setCreated_at(razorPayOrder.get("created_at"));
        orderRepository.save(order);
        order.setAmount(0);
        return order;
    }

    private int getAmount(String coupon) {
        if (couponRepository.findById(coupon).isPresent()) {
            return 0;
        }
        return 1;
    }

    public Price getPrice(String coupon){
        if (couponRepository.findById(coupon).isPresent()) {
            return new Price(0, true);
        }
        return new Price(1, false);    }

    public Order checkOrder(UserMawala userMawala, String orderId) throws RazorpayException {
        Order order = orderRepository.getReferenceById(orderId);
        if (order.getUser().getFirebaseId().equals(userMawala.getFirebaseId())) {
            com.razorpay.Order razorPayOrder = razorPayService.checkOrderStatus(order);
            order.setOrderStatus(razorPayOrder.get("status"));
            if (razorPayOrder.get("status").equals(ORDER_COMPLETED)) {
                userMawala.setPremium(true);
                userRepository.save(userMawala);
            }
            orderRepository.save(order);
            return order;
        } else {
            throw new RuntimeException("Order Doesn't belong to the user");
        }
    }
}
