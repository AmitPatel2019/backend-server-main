package com.asdsoft.mavala.repository;

import com.asdsoft.mavala.entity.Podcast;
import com.asdsoft.mavala.entity.PodcastGroups;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PodcastGroupRepository extends JpaRepository<PodcastGroups, Long> {
}
