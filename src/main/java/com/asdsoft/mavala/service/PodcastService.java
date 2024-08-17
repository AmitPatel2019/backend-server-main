package com.asdsoft.mavala.service;

import com.asdsoft.mavala.entity.Podcast;
import com.asdsoft.mavala.entity.PodcastGroups;
import com.asdsoft.mavala.repository.PodcastGroupRepository;
import com.asdsoft.mavala.repository.PodcastRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PodcastService {
    @Autowired
    private PodcastRepository podcastRepository;

    @Autowired
    private PodcastGroupRepository podcastGroupRepository;

    public List<Podcast> getAllPodCast(String lan, String podcast_group) {
        return podcastRepository.findAll().stream().filter(podcast -> podcast.getLang().equals(lan) && podcast.getPodcast_group().equals(podcast_group)).collect(Collectors.toList());
    }

    public List<PodcastGroups> getAllPodCastGroups() {
        return podcastGroupRepository.findAll();
    }

}
