package com.asdsoft.mavala.data;

import com.asdsoft.mavala.entity.DurgCampaignImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserImages {
    private Long id;
    private String imageName;

    private String first_name;
    private String last_name;

    public UserImages(DurgCampaignImage durgCampaignImage) {
        this.id = durgCampaignImage.getId();
        this.imageName = durgCampaignImage.getFile_name();
    }
}
