package com.asdsoft.mavala.service;

import com.asdsoft.mavala.data.PaymentDetails;
import com.asdsoft.mavala.data.Price;
import com.asdsoft.mavala.entity.Coupon;
import com.asdsoft.mavala.entity.Order;
import com.asdsoft.mavala.entity.UserMawala;
import com.asdsoft.mavala.repository.CouponRepository;
import com.asdsoft.mavala.repository.OrderRepository;
import com.asdsoft.mavala.repository.UserRepository;
import com.asdsoft.mavala.utils.CommonUtil;
import com.razorpay.RazorpayException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class OrderService {
    private final static String ORDER_COMPLETED = "paid";
    private final static String ORDER_AUTHORIZED= "authorized";
    private final static String ORDER_CAPTURED= "captured";


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
        if (!CommonUtil.isNullOrEmpty(coupon)){
            int count=checkCouponById(coupon);
            if (count==1){
               updateCouponCount(coupon, 1);
                order.setCoupon(coupon);
            }
        }
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
        String status=razorPayOrder.get("status");
        log.info("status {}", status);
        log.info("user {}", principal);

        if (razorPayOrder.get("status").equals(ORDER_COMPLETED)) {
            principal.setPremium(true);
            principal.setLocked(false);
            userRepository.save(principal);
        }
        return order;
    }

    private int getAmount(String coupon) {
        if (couponRepository.findById(coupon).isPresent()) {
            return 0;
        }
        return 1;
    }

    public Price getPrice(String coupon){
        if (!CommonUtil.isNullOrEmpty(coupon)){
            int count=checkCouponById(coupon);
            if (count==1){
                return new Price(0, true);
            }
        }else {
            return new Price(399, true);
        }
       /* if (couponRepository.findById(coupon).isPresent()) {
            return new Price(399, true);
        }
        return new Price(1, false);  */
        return new Price(399, true);
    }

    public Order checkOrder(UserMawala userMawala, String orderId) throws RazorpayException {
        Order order = orderRepository.getReferenceById(orderId);
              if (order.getUser().getFirebaseId().equals(userMawala.getFirebaseId())) {
             com.razorpay.Order razorPayOrder = razorPayService.checkOrderStatus(order);
            order.setOrderStatus(razorPayOrder.get("status"));
            if (razorPayOrder.get("status").equals(ORDER_COMPLETED) ||
                    razorPayOrder.get("status").equals(ORDER_CAPTURED) ||
                    razorPayOrder.get("status").equals(ORDER_AUTHORIZED)) {
                userMawala.setPremium(true);
                userMawala.setLocked(false);
                userRepository.save(userMawala);
            }
            log.info("userMawala: {}", userMawala);
            log.info("Order: {}", order);
                  orderRepository.save(order);
            return order;
        } else {
            throw new RuntimeException("Order Doesn't belong to the user");
        }
    }

    public Order checkOrderDetails(UserMawala userMawala, PaymentDetails paymentDetails) throws RazorpayException {
       // Order order = orderRepository.getReferenceById(paymentDetails.getOrderId());
        Order order = orderRepository.findById(paymentDetails.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + paymentDetails.getOrderId()));

        if(userMawala.getFirebaseId()!=null){
         //   com.razorpay.Order razorPayOrder = razorPayService.checkOrderStatus(order);
            String url1=razorPayService.getPaymentDetailsByPaymentId(paymentDetails.getPaymentId());
          //  order.setOrderStatus(razorPayOrder.get("status"));
            if (url1.equals(ORDER_COMPLETED) ||
                    url1.equals(ORDER_CAPTURED) ||
                    url1.equals(ORDER_AUTHORIZED)) {
                userMawala.setPremium(true);
                userMawala.setLocked(false);
                userRepository.save(userMawala);
            }
          //  log.info("userMawala: {}", userMawala);
            order.setOrderStatus(url1);
            order.setPaymentId(paymentDetails.getPaymentId());
            log.info("Order: {}", order);
            orderRepository.save(order);
            return order;
        } else {
            throw new RuntimeException("Order Doesn't belong to the user");
        }

     }

     public Coupon findCouponByCode(String code){
        Coupon coupon=couponRepository.findByCode(code);
        if (!CommonUtil.isNullOrEmpty(coupon)){
            return  coupon;
        }
        return null;
     }

     public Coupon updateCouponCount(String couponCode, int count){
         Coupon coupon=couponRepository.findByCode(couponCode);
         if (!CommonUtil.isNullOrEmpty(coupon)){
             int totalCount = Optional.ofNullable(coupon.getUsedCount()).orElse(0) + count;
             coupon.setUsedCount(totalCount);
             return  coupon;
         }
         return null;
     }

     public int checkCouponById(String couponCode){
         Coupon coupon=couponRepository.findByCode(couponCode);
         if (!CommonUtil.isNullOrEmpty(coupon)){
             if (couponCode.equals("AE2023")){
                 if (CommonUtil.isNullOrEmpty(coupon.getUsedCount()) || coupon.getUsedCount() <= 1500){
                     return 1;
                 }
             }
             if (CommonUtil.isNullOrEmpty(coupon.getUsedCount()) || coupon.getUsedCount()== 0){
                 return 1;
             }
         }
        return 0;
     }

    public static boolean isNotNull(Object obj) {
        return obj != null;
    }
}
