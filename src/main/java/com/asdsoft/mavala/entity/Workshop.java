package com.asdsoft.mavala.entity;

import com.asdsoft.mavala.data.WorkshopData;
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
public class Workshop {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_mawala_firebase_id")
    private UserMawala userMawala;
    private String attendeeName;
    private String emailAddress;
    private String timeSlot;
    private int age;
    private String booking_id;
    private long workshopId;

    public UserMawala getUserMawala() {
        return userMawala;
    }

    public void setUserMawala(UserMawala userMawala) {
        this.userMawala = userMawala;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
