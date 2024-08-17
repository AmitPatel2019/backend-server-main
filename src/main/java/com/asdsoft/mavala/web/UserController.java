package com.asdsoft.mavala.web;


import com.asdsoft.mavala.data.UserBoardRequest;
import com.asdsoft.mavala.data.UserRequest;
import com.asdsoft.mavala.entity.UserMawala;
import com.asdsoft.mavala.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@SecurityRequirement(name = "google-jwt")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserMawala login(@RequestBody UserRequest userRequest) {
         return userService.getUser(getUser(), userRequest);
    }

    @PostMapping("/user/details")
    public UserMawala extraUserDetails(@RequestBody UserBoardRequest userBoardRequest) {
        return userService.addExtraUserDetails(getUserData(), userBoardRequest);
    }

    @GetMapping("/user")
    public UserMawala getUserDetails(){
        return getUserData();
    }

    @GetMapping("/users")
    public List<UserMawala> getAllUser() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/users")
    public boolean deleteAllUsers() {
        try {
            userService.deleteAllUsers();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @PostMapping(value = "/user/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public UserMawala uploadImage(@RequestParam("photo") MultipartFile image) throws IOException {
        return userService.uploadImage(getUserData(), image);
    }

    @GetMapping(value = "/user/image", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public byte[] getUserImage() throws IOException {
        return userService.getUserImage(getUserData());
    }
}
