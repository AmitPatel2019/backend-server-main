package com.asdsoft.mavala.setup;

import com.asdsoft.mavala.data.WarriorAudio;
import com.asdsoft.mavala.entity.*;
import com.asdsoft.mavala.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DbSetup {
    private final static String CELEB_FILE_NAME = "data/celebrity.json";
    private final static String PODCAST_FILE_NAME = "data/podcast.json";
    private final static String PODCAST_GROUP_FILE_NAME = "data/podcast_groups.json";
    private final static String WORKSHOP_FILE_NAME = "data/Workshops.json";
    private final static String WARRIOR_FILE_NAME = "data/warriors.json";
    private final static String AUDIO_FILE_NAME = "data/audios.json";
    private final static String WARRIOR_AUDIO_FILE_NAME = "data/warriors-audio.json";
    private final static String COUPON_CODES_FILE = "data/codes.json";

    private final static String WAR_STORIES_FILE = "data/war-stories.json";


    @Autowired
    private CelebrityRepository celebrityRepository;
    @Autowired
    private PodcastRepository podcastRepository;
    @Autowired
    private WarriorRepository warriorRepository;
    @Autowired
    private AudioRepository audioRepository;
    @Autowired
    private WarriorAudioRepository warriorAudioRepository;

    @Autowired
    private PodcastGroupRepository podcastGroupRepository;

    @Autowired
    private WorkShopDataRepository workshopRepository;

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private WarStoriesRepository warStoriesRepository;

    @PostConstruct
    public void addRepos() throws IOException {
        if (celebrityRepository.findAll().size() == 0) {
            InputStream inputStream = Objects.requireNonNull(getClass().getClassLoader().getResource(CELEB_FILE_NAME)).openStream();
            ObjectMapper mapper = new ObjectMapper();
            Celebrity[] celebrities = mapper.readValue(inputStream, Celebrity[].class);
            for (Celebrity celebrity : celebrities) {
                celebrityRepository.save(celebrity);
            }
        }
        if (podcastRepository.findAll().size() == 0) {
            InputStream inputStream = Objects.requireNonNull(getClass().getClassLoader().getResource(PODCAST_FILE_NAME)).openStream();
            ObjectMapper mapper = new ObjectMapper();
            Podcast[] podcasts = mapper.readValue(inputStream, Podcast[].class);
            for (Podcast podcast : podcasts) {
                podcastRepository.save(podcast);
            }
        }
        if (podcastGroupRepository.findAll().size() == 0) {
            InputStream inputStream = Objects.requireNonNull(getClass().getClassLoader().getResource(PODCAST_GROUP_FILE_NAME)).openStream();
            ObjectMapper mapper = new ObjectMapper();
            PodcastGroups[] podcasts = mapper.readValue(inputStream, PodcastGroups[].class);
            for (PodcastGroups podcast : podcasts) {
                podcastGroupRepository.save(podcast);
            }
        }
        if (workshopRepository.findAll().size() == 0) {
            InputStream inputStream = Objects.requireNonNull(getClass().getClassLoader().getResource(WORKSHOP_FILE_NAME)).openStream();
            ObjectMapper mapper = new ObjectMapper();
            WorkShopData[] workShopDataList = mapper.readValue(inputStream, WorkShopData[].class);
            for (WorkShopData workShopData : workShopDataList) {
                workshopRepository.save(workShopData);
            }
        }
        if (warriorRepository.findAll().size() == 0) {
            InputStream inputStream = Objects.requireNonNull(getClass().getClassLoader().getResource(WARRIOR_FILE_NAME)).openStream();
            ObjectMapper mapper = new ObjectMapper();
            Warrior[] warriors = mapper.readValue(inputStream, Warrior[].class);
            for (Warrior warrior : warriors) {
                warriorRepository.save(warrior);
            }
        }
        if (audioRepository.findAll().size() == 0) {
            InputStream inputStream = Objects.requireNonNull(getClass().getClassLoader().getResource(AUDIO_FILE_NAME)).openStream();
            ObjectMapper mapper = new ObjectMapper();
            Audio[] audios = mapper.readValue(inputStream, Audio[].class);
            for (Audio audio : audios) {
                audioRepository.save(audio);
            }
        }
        if (warriorRepository.findAll().size() == 0) {
            InputStream inputStream = Objects.requireNonNull(getClass().getClassLoader().getResource(AUDIO_FILE_NAME)).openStream();
            ObjectMapper mapper = new ObjectMapper();
            Audio[] audios = mapper.readValue(inputStream, Audio[].class);
            for (Audio audio : audios) {
                audioRepository.save(audio);
            }
        }
        if (warStoriesRepository.findAll().size() == 0) {
            InputStream inputStream = Objects.requireNonNull(getClass().getClassLoader().getResource(WAR_STORIES_FILE)).openStream();
            ObjectMapper mapper = new ObjectMapper();
            List<String> names = mapper.readValue(inputStream, List.class);
            for (String name : names) {
                warStoriesRepository.save(WarStories.builder().title(name.replace(".mp4", "")).link(String.format("https://starfish-app-trwgh.ondigitalocean.app/War Stories/%s", name)).build());
            }
        }
        if (warriorAudioRepository.findAll().size() == 0) {
            InputStream inputStream = Objects.requireNonNull(getClass().getClassLoader().getResource(WARRIOR_AUDIO_FILE_NAME)).openStream();
            ObjectMapper mapper = new ObjectMapper();
            WarriorAudio[] warriorAudios = mapper.readValue(inputStream, WarriorAudio[].class);
            for (WarriorAudio warriorAudio : warriorAudios) {
                Warrior warrior = warriorRepository.findAll().stream().filter(warrior1 -> warrior1.getName().equals(warriorAudio.getName())).collect(Collectors.toList()).get(0);
                for (String audios : warriorAudio.getAudios()) {
                    com.asdsoft.mavala.entity.WarriorAudio warriorAudio1 = new com.asdsoft.mavala.entity.WarriorAudio();
                    warriorAudio1.setWarrior(warrior);
                    warriorAudio1.setAudio(audioRepository.findAll().stream().filter(audio -> audio.getName().equals(audios)).collect(Collectors.toList()).get(0));
                    warriorAudioRepository.save(warriorAudio1);
                }
            }
        }
        if (couponRepository.findAll().size() == 0){
            InputStream inputStream = Objects.requireNonNull(getClass().getClassLoader().getResource(COUPON_CODES_FILE)).openStream();
            ObjectMapper mapper = new ObjectMapper();
            Coupon[] coupons = mapper.readValue(inputStream, Coupon[].class);
            for (Coupon coupon : coupons) {
                couponRepository.save(coupon);
            }
        }
    }
}
