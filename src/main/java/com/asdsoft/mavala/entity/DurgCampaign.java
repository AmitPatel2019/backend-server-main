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
public class DurgCampaign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToOne
    @JoinColumn(name = "user_firebase_id")
    private UserMawala userMawala;
    private boolean is_certificate_ready;
    private String first_name;
    private String last_name;
    private String email;
    @ManyToOne
    @JoinColumn(name = "celebrity_id")
    private Celebrity celebrity;
}
