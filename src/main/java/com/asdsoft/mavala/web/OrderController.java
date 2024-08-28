package com.asdsoft.mavala.web;
import com.asdsoft.mavala.data.OrderResponse;
import com.asdsoft.mavala.data.Price;
import com.asdsoft.mavala.service.OrderService;
import com.razorpay.RazorpayException;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@SecurityRequirement(name = "google-jwt")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/get/price")
    @ResponseBody
    public Price getPrice(@RequestParam String coupon) {
        return orderService.getPrice(coupon);
    }

    @PostMapping("/order")
    public OrderResponse createOrder(@RequestParam(value = "coupon", required = false) String coupon) throws RazorpayException {
        if (StringUtils.isEmpty(coupon)){
            return new OrderResponse(orderService.createOrder(getUserData(), "N"));
        }
        return new OrderResponse(orderService.createOrder(getUserData(), coupon));
    }

    @GetMapping("/order/{orderId}")
    public OrderResponse checkOrder(@PathVariable("orderId") String orderId) throws RazorpayException {
        return new OrderResponse(orderService.checkOrder(getUserData(), orderId));
    }
}