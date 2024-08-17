package com.asdsoft.mavala.data;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class WorkshopData {
    private String attendeeName;
    private String emailAddress;
    private String timeSlot;
    private int age;
    private long workshopId;
}
