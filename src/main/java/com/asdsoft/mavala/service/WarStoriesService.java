package com.asdsoft.mavala.service;

import com.asdsoft.mavala.entity.WarStories;
import com.asdsoft.mavala.repository.WarStoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WarStoriesService {
    @Autowired
    private WarStoriesRepository warStoriesRepository;

    public List<WarStories> getAllWarStories() {
        return warStoriesRepository.findAll();
    }
}
