package com.asdsoft.mavala.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WarriorAudio {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "warrior_id")
    private Warrior warrior;
    @ManyToOne
    @JoinColumn(name = "audio_id")
    private Audio audio;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
