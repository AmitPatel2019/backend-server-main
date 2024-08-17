package com.asdsoft.mavala.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DynamicCardData {
    private String id;
    private String name;
    private String link;
    private String desc;
    private String image;
    private String lan;
    private boolean isVertical = true;
}
