package com.asdsoft.mavala.repository;

import com.asdsoft.mavala.entity.Celebrity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CelebrityRepository extends JpaRepository<Celebrity, Long> {
}
