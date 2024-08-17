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
public class BoardDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_mawala_firebase_id")
    private UserMawala userMawala;
    private String boardId;
    private String boardSeller;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
