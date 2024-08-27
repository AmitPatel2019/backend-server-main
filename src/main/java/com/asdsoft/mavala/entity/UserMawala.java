package com.asdsoft.mavala.entity;

import lombok.*;
import org.springframework.web.bind.annotation.GetMapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
public class UserMawala {
    @Id
    private String firebaseId;
    @Column(unique=true)
    private String phoneNumber;
    private String firebaseNotifyToken;
    private boolean isPremium;
    private String first_name;
    private String last_name;
    private String boardId;
    private String email;
    private String address;
    private String imageLink;
    private byte[] image;
    private String pinCode;
    private String city;
    private String state;
    private String country;
    private Boolean locked;
}
