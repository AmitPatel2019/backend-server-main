package com.asdsoft.mavala.razorpay;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RazorPayConfig {
    @Value("${razor_pay_key}")
    private String razor_pay_key;
    @Value("${razor_pay_sec}")
    private String razor_pay_sec;

    @Bean
    public RazorpayClient getRazorpayClient() throws RazorpayException {
        return new RazorpayClient(razor_pay_key, razor_pay_sec);
    }
}
