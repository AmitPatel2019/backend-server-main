package com.asdsoft.mavala.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DynamicCard {
    private String id;
    private String name;
    private String image;
    private String desc;
    private String type;
    private boolean visible;
    private boolean locked;
}
