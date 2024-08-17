package com.asdsoft.mavala.repository;

import com.asdsoft.mavala.entity.UserMawala;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserMawala, String> {
}
