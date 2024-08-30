package com.asdsoft.mavala.repository;

import com.asdsoft.mavala.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, String> {

    Coupon findByCode(String code);

}
