package com.asdsoft.mavala.repository;

import com.asdsoft.mavala.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {
}
