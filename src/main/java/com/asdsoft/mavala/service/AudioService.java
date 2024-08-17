package com.asdsoft.mavala.service;

import com.asdsoft.mavala.entity.Audio;
import com.asdsoft.mavala.repository.AudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AudioService {
    @Autowired
    private AudioRepository audioRepository;

    public List<Audio> getAllAudioList() {
        return audioRepository.findAll();
    }

    public List<Audio> getAllAudioList(String lang) {
        return audioRepository.findAll().stream().filter(audio -> audio.getLang().equals(lang)).collect(Collectors.toList());

    }
}
