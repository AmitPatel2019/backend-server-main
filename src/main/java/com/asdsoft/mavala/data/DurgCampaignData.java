package com.asdsoft.mavala.data;

import com.asdsoft.mavala.entity.Celebrity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DurgCampaignData {
    private boolean is_active;
    private boolean is_data_uploaded;
    private boolean is_image_uploaded;
    private boolean is_certificate_ready;
    private String userCampaignId;

    private List<Celebrity> celebrityList;

    public boolean isIs_certificate_ready() {
        return is_certificate_ready;
    }

    public void setIs_certificate_ready(boolean is_certificate_ready) {
        this.is_certificate_ready = is_certificate_ready;
    }

    public List<Celebrity> getCelebrityList() {
        return celebrityList;
    }

    public void setCelebrityList(List<Celebrity> celebrityList) {
        this.celebrityList = celebrityList;
    }

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public boolean isIs_data_uploaded() {
        return is_data_uploaded;
    }

    public void setIs_data_uploaded(boolean is_data_uploaded) {
        this.is_data_uploaded = is_data_uploaded;
    }

    public boolean isIs_image_uploaded() {
        return is_image_uploaded;
    }

    public void setIs_image_uploaded(boolean is_image_uploaded) {
        this.is_image_uploaded = is_image_uploaded;
    }

    public String getUserCampaignId() {
        return userCampaignId;
    }

    public void setUserCampaignId(String userCampaignId) {
        this.userCampaignId = userCampaignId;
    }
}
