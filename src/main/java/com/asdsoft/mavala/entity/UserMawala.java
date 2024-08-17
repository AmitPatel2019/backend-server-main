package com.asdsoft.mavala.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
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
    private String pincode;
    private String city;
    private String state;
    private String country;
}
