package com.asdsoft.mavala.repository;

import com.asdsoft.mavala.entity.Podcast;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PodcastRepository extends JpaRepository<Podcast, Long> {
}
