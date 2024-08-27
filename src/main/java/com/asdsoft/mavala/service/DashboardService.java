package com.asdsoft.mavala.service;

import com.asdsoft.mavala.data.DashboardCard;
import com.asdsoft.mavala.data.DynamicCard;
import com.asdsoft.mavala.data.DynamicCardData;
import com.asdsoft.mavala.entity.Audio;
import com.asdsoft.mavala.entity.UserMawala;
import com.asdsoft.mavala.repository.AudioRepository;
import com.asdsoft.mavala.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Collections.EMPTY_LIST;

@Service
public class DashboardService {
    @Autowired
    private AudioRepository audioRepository;
    @Autowired
    private UserRepository userRepository;
    public List<DashboardCard> getDashBoardCards() {
        return Arrays.asList(

        );
    }

       public List<DynamicCard> getDynamicCard() {

        return Arrays.asList(new DynamicCard("1", "Mawala The Board game", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/Mawala - The Board Game.jpg", "Explaination of the board game and various modes to play it", "video",true, true),
                new DynamicCard("2", "Shiv Shatak - Chronicles of ShivChhatrapati Maharaj", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/100 Stories.jpg", "Brief historical context of 100 events from ShivChhatrapati's life", "audio", true, false),
                new DynamicCard("3", "Mawala - Online Curriculum", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/Mawala Workshop.jpg", "Interesting and engaging way to learn unheard historical chapters and animations", "video", true, true),
                new DynamicCard("4", "Mawala - The Pandora", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/War Stories.jpg", "Aspects of legends from the field about various topics of Shiv Era!", "video", true, false),
                new DynamicCard("5", "Battle of Swarajya", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/War Stories.jpg", "Series of Docudramas to learn battle fought by ShivChhatrapati", "video", true, true),
                new DynamicCard("6", "Shiv Bhushan Strings", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/Shiv Bhushan Strings.jpg", "Elaborations of KaviBhushan's Poems on ShivChhatrapati", "video",true, true),
                new DynamicCard("7", "स्वराज्याची भूषणे", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/स्वराज्याची भूषणे.jpg", "Information about forts of Sahyadry", "video", true, false),
                new DynamicCard("8","Swarriors - A Tale of Swarajya Superheroes Battles of Swarajya Battles of Swarajya", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/Swarriors.jpg", "Introduction to Real Indian Superheroes", "video", true, true));
//    return EMPTY_LIST;
  }

    public List<DynamicCardData> getDynamicCardData(String id) {
        if (id.equals("2")) {
            return getAudios();
        }
        if (id.equals("5")) {
            return Collections.singletonList(new DynamicCardData("1", "Video 1", "https://starfish-app-trwgh.ondigitalocean.app/War%20Stories/Intro.mp4", "test", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/100 Stories.jpg", "english", true));
        }
        else{
            return Collections.singletonList(new DynamicCardData("1", "Video 1", "https://starfish-app-trwgh.ondigitalocean.app/War%20Stories/Intro.mp4", "test", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/100 Stories.jpg", "english", true));
        }
    }

    private List<DynamicCardData> getAudios(){
        return audioRepository.findAll().stream().map(this::getDynamicCard).collect(Collectors.toList());
    }

    private DynamicCardData getDynamicCard(Audio audio){
        return new DynamicCardData(String.valueOf(audio.getId()), audio.getName(), audio.getLink(), audio.getDesc(), audio.getImage(), audio.getLang(), false);
    }

    public List<DynamicCard> getDynamicCard(Jwt user) {
      Optional<UserMawala> userMawala = userRepository.findById(user.getSubject());

      if (!userMawala.isPresent()){
          return Arrays.asList();
      }

        boolean isLocked = userMawala.get().getLocked();
        return Arrays.asList(
                new DynamicCard("1", "Mawala The Board game", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/Mawala - The Board Game.jpg", "Explanation of the board game and various modes to play it", "video", true, isLocked),
                new DynamicCard("2", "Shiv Shatak - Chronicles of ShivChhatrapati Maharaj", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/100 Stories.jpg", "Brief historical context of 100 events from ShivChhatrapati's life", "audio", true, isLocked),
                new DynamicCard("3", "Mawala - Online Curriculum", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/Mawala Workshop.jpg", "Interesting and engaging way to learn unheard historical chapters and animations", "video", true, isLocked),
                new DynamicCard("4", "Mawala - The Pandora", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/War Stories.jpg", "Aspects of legends from the field about various topics of Shiv Era!", "video", true, isLocked),
                new DynamicCard("5", "Battle of Swarajya", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/War Stories.jpg", "Series of Docudramas to learn battles fought by ShivChhatrapati", "video", true, isLocked),
                new DynamicCard("6", "Shiv Bhushan Strings", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/Shiv Bhushan Strings.jpg", "Elaborations of KaviBhushan's Poems on ShivChhatrapati", "video", true, isLocked),
                new DynamicCard("7", "स्वराज्याची भूषणे", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/स्वराज्याची भूषणे.jpg", "Information about forts of Sahyadry", "video", true, isLocked),
                new DynamicCard("8", "Swarriors - A Tale of Swarajya Superheroes", "https://starfish-app-trwgh.ondigitalocean.app/Ticket/Swarriors.jpg", "Introduction to Real Indian Superheroes", "video", true, isLocked)
        );
    }
}
