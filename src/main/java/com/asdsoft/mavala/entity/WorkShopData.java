package com.asdsoft.mavala.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WorkShopData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String title;
    private String image;
    private String day;
    private String language;
    @ElementCollection
    private List<String> timeSlots;
    private String zoomLink;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
