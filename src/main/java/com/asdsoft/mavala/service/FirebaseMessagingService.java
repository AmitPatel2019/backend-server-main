package com.asdsoft.mavala.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FirebaseMessagingService {

    private final FirebaseMessaging firebaseMessaging;

    public FirebaseMessagingService(FirebaseMessaging firebaseMessaging) {
        this.firebaseMessaging = firebaseMessaging;
    }


    public String sendNotification(String subject, String body, Map<String, String> data, String token) throws FirebaseMessagingException {

        Notification notification = Notification.builder().setTitle(subject).setBody(body).build();

        Message message = Message.builder().setToken(token).setNotification(notification).putAllData(data).build();

        return firebaseMessaging.send(message);
    }

}