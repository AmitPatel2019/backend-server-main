package com.asdsoft.mavala.scheduled;

import com.asdsoft.mavala.entity.UserMawala;
import com.asdsoft.mavala.repository.DurgCampaignRepository;
import com.asdsoft.mavala.service.FirebaseMessagingService;
import com.google.firebase.messaging.FirebaseMessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class CertificateJob {
    @Autowired
    private DurgCampaignRepository durgCampaignRepository;
    @Autowired
    private FirebaseMessagingService firebaseMessagingService;
    @Value("${notification_title}")
    private String notification_title;
    @Value("${notification_body}")
    private String notification_body;

    @Scheduled(cron = "0 0 7 * * *")
    public void generateCertificate() {
        durgCampaignRepository.findAll().stream().filter(durgCampaign -> !durgCampaign.is_certificate_ready()).collect(Collectors.toList()).forEach(durgCampaign -> {
            sendNotification(durgCampaign.getUserMawala());
            durgCampaign.set_certificate_ready(true);
            durgCampaignRepository.save(durgCampaign);
        });
    }

    private void sendNotification(UserMawala userMawala) {
        try {
            firebaseMessagingService.sendNotification(String.format(notification_title, userMawala.getFirst_name()), notification_body, new HashMap<>(), userMawala.getFirebaseNotifyToken());
        } catch (FirebaseMessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
