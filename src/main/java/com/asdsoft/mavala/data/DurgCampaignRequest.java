package com.asdsoft.mavala.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DurgCampaignRequest {
    private String first_name;
    private String last_name;
    private String email;
    private String celebrityId;
}
