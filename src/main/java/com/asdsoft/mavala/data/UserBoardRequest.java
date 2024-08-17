package com.asdsoft.mavala.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserBoardRequest {
    private String first_name;
    private String last_name;
    private String address;
    private String email;
    private String boardId;
    private String pincode;
    private String city;
    private String state;
    private String country;
}
