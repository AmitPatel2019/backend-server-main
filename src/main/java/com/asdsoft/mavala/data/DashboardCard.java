package com.asdsoft.mavala.data;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class DashboardCard {
    private String name;
    private String image;
    private String desc;
    private boolean visible;
    private boolean locked;
}
