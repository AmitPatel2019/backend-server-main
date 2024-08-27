package com.asdsoft.mavala.service;

import com.asdsoft.mavala.data.BoardInfo;
import com.asdsoft.mavala.data.UserBoardRequest;
import com.asdsoft.mavala.data.UserRequest;
import com.asdsoft.mavala.entity.UserMawala;
import com.asdsoft.mavala.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserMawala getUser(Jwt principal, UserRequest userRequest) {
        UserMawala userMawala;
        try {
            userMawala = userRepository.findById(principal.getSubject()).get();
        } catch (Exception e) {
            log.info("Creating a new userMawala");
            userMawala = UserMawala.builder().firebaseId(principal.getSubject()).phoneNumber(principal.getClaimAsString("phone_number")).build();
            userRepository.save(userMawala);
        }
        userMawala.setFirebaseNotifyToken(userRequest.getFirebaseNotifyToken());
        userRepository.save(userMawala);
        return userMawala;
    }

    public UserMawala addExtraUserDetails(UserMawala userMawala, UserBoardRequest userBoardRequest) {
        userMawala.setEmail(userBoardRequest.getEmail());
        userMawala.setFirst_name(userBoardRequest.getFirst_name());
        userMawala.setLast_name(userBoardRequest.getLast_name());
        userMawala.setAddress(userBoardRequest.getAddress());
        userMawala.setCity(userBoardRequest.getCity());
        userMawala.setPinCode(userBoardRequest.getPincode());
        userMawala.setState(userBoardRequest.getState());
        userMawala.setCountry(userBoardRequest.getCountry());
        if (userBoardRequest.getBoardId() != null || userMawala.getBoardId() != null) {
            userMawala.setBoardId(userBoardRequest.getBoardId());
        }
        userMawala.setImageLink(String.format("https://ui-avatars.com/api/?name=%s+%s", userBoardRequest.getFirst_name(), userBoardRequest.getLast_name()));
        userRepository.save(userMawala);
        return userMawala;
    }

    public BoardInfo updateBoardInfo(UserMawala userMawala, BoardInfo boardInfo) {
        userMawala.setBoardId(UUID.randomUUID().toString());
        userRepository.save(userMawala);
        return boardInfo;
    }

    public UserMawala getUser(Jwt principal) {
        return userRepository.findById(principal.getSubject()).get();
    }

    public List<UserMawala> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteAllUsers() {
        userRepository.deleteAll();
    }

    public boolean isUserPremium(Jwt user) {
        UserMawala userMawalaObject = userRepository.findById(user.getSubject()).get();
        return userMawalaObject.isPremium();
    }

    public boolean togglePremium(Jwt user) {
        UserMawala userMawalaObject = userRepository.findById(user.getSubject()).get();
        userMawalaObject.setPremium(true);
        userRepository.save(userMawalaObject);
        return userMawalaObject.isPremium();
    }

    public UserMawala uploadImage(UserMawala userData, MultipartFile image) throws IOException {
        userData.setImage(image.getBytes());
        userRepository.save(userData);
        return userData;
    }

    public byte[] getUserImage(UserMawala userData) {
        return userData.getImage();
    }
}
