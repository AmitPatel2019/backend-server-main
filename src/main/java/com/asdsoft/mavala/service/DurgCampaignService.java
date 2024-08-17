package com.asdsoft.mavala.service;

import com.asdsoft.mavala.data.DurgCampaignData;
import com.asdsoft.mavala.data.DurgCampaignRequest;
import com.asdsoft.mavala.data.UserCampaignImages;
import com.asdsoft.mavala.data.UserImages;
import com.asdsoft.mavala.entity.Celebrity;
import com.asdsoft.mavala.entity.DurgCampaign;
import com.asdsoft.mavala.entity.DurgCampaignImage;
import com.asdsoft.mavala.entity.UserMawala;
import com.asdsoft.mavala.repository.CelebrityRepository;
import com.asdsoft.mavala.repository.DurgCampaignImageRepository;
import com.asdsoft.mavala.repository.DurgCampaignRepository;
import com.asdsoft.mavala.repository.UserRepository;
import com.asdsoft.mavala.scheduled.CertificateJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DurgCampaignService {
    private List<Celebrity> celebrityList;
    @Value("${durg.campaign}")
    private boolean isDurgCampaignActive;
    @Autowired
    private DurgCampaignRepository durgCampaignRepository;
    @Autowired
    private DurgCampaignImageRepository durgCampaignImageRepository;
    @Autowired
    private CelebrityRepository celebrityRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CertificateJob certificateJob;
    @Autowired
    private ImageEditService imageEditService;

    public List<Celebrity> getCelebrityList() {
        if (celebrityList == null) {
            this.celebrityList = celebrityRepository.findAll();
        }
        return celebrityList;
    }

    public DurgCampaignData campaignDataReadyCertificate() {
        certificateJob.generateCertificate();
        return new DurgCampaignData();
    }

    public DurgCampaignData campaignData(UserMawala userMawala) {
        if (isDurgCampaignActive) {
            return new DurgCampaignData(true, isUserUploadedData(userMawala), isUserUploadedImage(userMawala), isCertReady(userMawala), getUserData(userMawala), getCelebrityList());
        }
        return new DurgCampaignData(false, false, false, isCertReady(userMawala), null, getCelebrityList());
    }

    public DurgCampaignData submitEntry(UserMawala userMawala, DurgCampaignRequest durgCampaignRequest) {
        try {
            userMawala.setFirst_name(durgCampaignRequest.getFirst_name());
            userMawala.setLast_name(durgCampaignRequest.getLast_name());
            DurgCampaign durgCampaign;
            if (!StringUtils.isEmpty(durgCampaignRequest.getCelebrityId())) {
                durgCampaign = DurgCampaign.builder().userMawala(userMawala).email(durgCampaignRequest.getEmail()).celebrity(getCelebrity(durgCampaignRequest.getCelebrityId())).first_name(durgCampaignRequest.getFirst_name()).last_name(durgCampaignRequest.getLast_name()).is_certificate_ready(true).build();
            } else {
                durgCampaign = DurgCampaign.builder().userMawala(userMawala).email(durgCampaignRequest.getEmail()).first_name(durgCampaignRequest.getFirst_name()).last_name(durgCampaignRequest.getLast_name()).is_certificate_ready(true).build();
            }
            durgCampaign.set_certificate_ready(false);
            durgCampaignRepository.save(durgCampaign);
            userRepository.save(userMawala);
            return new DurgCampaignData(true, isUserUploadedData(userMawala), isUserUploadedImage(userMawala), isCertReady(userMawala), String.valueOf(durgCampaign.getId()), getCelebrityList());
        } catch (Exception e) {
            log.error("Exception ", e);
        }
        return new DurgCampaignData(true, false, false, false, null, getCelebrityList());
    }

    public DurgCampaignData uploadImage(UserMawala userMawala, String userCampaignId, MultipartFile[] images) {
        try {
            DurgCampaign durgCampaign = durgCampaignRepository.findById(Long.parseLong(userCampaignId)).get();
            if (userMawala.getFirebaseId().equals(durgCampaign.getUserMawala().getFirebaseId())) {
                for (MultipartFile image : images) {
                    DurgCampaignImage image1 = DurgCampaignImage.builder().durgCampaign(durgCampaign).userMawala(userMawala).file_name(image.getName()).image(image.getBytes()).build();
                    durgCampaignImageRepository.save(image1);
                }
                return new DurgCampaignData(true, true, true, isCertReady(userMawala), userCampaignId, getCelebrityList());
            }
        } catch (Exception e) {
            log.error("Error ", e);
        }
        return new DurgCampaignData(true, isUserUploadedData(userMawala), false, false, userCampaignId, getCelebrityList());
    }

    public UserCampaignImages getUserImages(UserMawala userMawala) {
        List<UserImages> images = durgCampaignImageRepository.findAll().stream().filter(durgCampaignImage -> durgCampaignImage.getUserMawala().getFirebaseId().equals(userMawala.getFirebaseId())).map(UserImages::new).collect(Collectors.toList());
        return new UserCampaignImages(userMawala.getFirst_name(), userMawala.getLast_name(), images);
    }

    public byte[] getUserCertificate(UserMawala userMawala) {
        DurgCampaign durgCampaign = durgCampaignRepository.findAll().stream().filter(durgCampaign1 -> durgCampaign1.getUserMawala().getFirebaseId().equals(userMawala.getFirebaseId())).collect(Collectors.toList()).get(0);
        if (durgCampaign.is_certificate_ready()) {
            try {
                return imageEditService.editImage(userMawala.getFirst_name(), userMawala.getLast_name());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("UserMawala Certificate is not ready");
    }

    public byte[] getUserImages(UserMawala userMawala, Long imageId) {
        Optional<DurgCampaignImage> durgCampaignImage = durgCampaignImageRepository.findById(imageId);
        if (durgCampaignImage.isPresent() && durgCampaignImage.get().getUserMawala().getFirebaseId().equals(userMawala.getFirebaseId())) {
            return durgCampaignImage.get().getImage();
        }
        throw new RuntimeException("Image not found");
    }

    private Celebrity getCelebrity(String id) {
        return celebrityRepository.findById(Long.valueOf(id)).get();
    }

    private String getUserData(UserMawala userMawala) {
        try {
            return String.valueOf(durgCampaignRepository.findAll().stream().filter(durgCampaign -> durgCampaign.getUserMawala().getFirebaseId().equals(userMawala.getFirebaseId())).collect(Collectors.toList()).get(0).getId());
        } catch (Exception e) {
            return null;
        }
    }

    private boolean isUserUploadedData(UserMawala userMawala) {
        long count = durgCampaignRepository.findAll().stream().filter(durgCampaign -> durgCampaign.getUserMawala().getFirebaseId().equals(userMawala.getFirebaseId())).count();
        return count > 0;
    }

    private boolean isUserUploadedImage(UserMawala userMawala) {
        long count = durgCampaignImageRepository.findAll().stream().filter(durgCampaign -> durgCampaign.getUserMawala().getFirebaseId().equals(userMawala.getFirebaseId())).count();
        return count > 0;
    }

    private boolean isCertReady(UserMawala userMawala) {
        try {
            DurgCampaign val = durgCampaignRepository.findAll().stream().filter(durgCampaign -> durgCampaign.getUserMawala().getFirebaseId().equals(userMawala.getFirebaseId())).collect(Collectors.toList()).get(0);
            return val.is_certificate_ready();
        } catch (Exception e) {
            log.error("Error ", e);
        }
        return false;
    }
}
