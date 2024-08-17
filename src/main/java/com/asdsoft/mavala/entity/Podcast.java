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
public class Podcast {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String name;
    private String desc;
    private String link;
    private String image;
    private String lang;
    private String podcast_group;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
