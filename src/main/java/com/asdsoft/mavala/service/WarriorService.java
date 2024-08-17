package com.asdsoft.mavala.service;

import com.asdsoft.mavala.entity.Audio;
import com.asdsoft.mavala.entity.Warrior;
import com.asdsoft.mavala.entity.WarriorAudio;
import com.asdsoft.mavala.repository.WarriorAudioRepository;
import com.asdsoft.mavala.repository.WarriorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WarriorService {
    @Autowired
    private WarriorRepository warriorRepository;

    @Autowired
    private WarriorAudioRepository warriorAudioRepository;

    public List<Warrior> getAllWarriors(String lang) {
        return warriorRepository.findAll().stream().filter(warrior -> warrior.getLang().equals(lang)).collect(Collectors.toList());
    }

    public List<Warrior> getAllWarriors() {
        return warriorRepository.findAll();
    }

    public List<Audio> getAllAudioForWarrior(String warrior_name) {
        return warriorAudioRepository.findAll().stream().filter(warriorAudio -> warriorAudio.getWarrior().getName().equals(warrior_name)).map(WarriorAudio::getAudio).collect(Collectors.toList());
    }

    public List<Audio> getAllAudioForWarrior(String warrior_name, String lang) {
        return warriorAudioRepository.findAll().stream().filter(warriorAudio -> {
            return warriorAudio.getWarrior().getName().equals(warrior_name) && warriorAudio.getWarrior().getLang().equals(lang);
        }).map(WarriorAudio::getAudio).collect(Collectors.toList());
    }
}
