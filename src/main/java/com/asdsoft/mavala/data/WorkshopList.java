package com.asdsoft.mavala.data;

import com.asdsoft.mavala.entity.WorkShopData;
import com.asdsoft.mavala.entity.Workshop;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WorkshopList {
    private Long id;
    private String title;
    private String image;
    private String day;
    private String language;
    private List<String> timeSlots;
    private boolean register;
    private String zoomLink;
    private String attendeeName;
    private String emailAddress;
    private String userTimeSlot;
    private int age;
    private String booking_id;

    public WorkshopList(WorkShopData workshopData) {
        this.id = workshopData.getId();
        this.title = workshopData.getTitle();
        this.day = workshopData.getDay();
        this.language = workshopData.getLanguage();
        this.timeSlots = workshopData.getTimeSlots();
        this.register = false;
        this.image = workshopData.getImage();
        this.zoomLink = workshopData.getZoomLink();
    }

    public WorkshopList(WorkShopData workshopData, Workshop workshop) {
        this.id = workshopData.getId();
        this.title = workshopData.getTitle();
        this.day = workshopData.getDay();
        this.language = workshopData.getLanguage();
        this.timeSlots = workshopData.getTimeSlots();
        this.register = true;
        this.image = workshopData.getImage();
        this.zoomLink = workshopData.getZoomLink();
        this.attendeeName = workshop.getUserMawala().getFirst_name() + " " + workshop.getUserMawala().getLast_name();
        this.emailAddress = workshop.getEmailAddress();
        this.userTimeSlot = workshop.getTimeSlot();
        this.age = workshop.getAge();
        this.booking_id = workshop.getBooking_id();
    }
}
