package com.asdsoft.mavala.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCampaignImages {
    private String first_name;
    private String last_name;
    private List<UserImages> images;
}
