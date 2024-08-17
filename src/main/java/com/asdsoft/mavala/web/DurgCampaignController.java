package com.asdsoft.mavala.web;

import com.asdsoft.mavala.data.DurgCampaignData;
import com.asdsoft.mavala.data.DurgCampaignRequest;
import com.asdsoft.mavala.data.UserCampaignImages;
import com.asdsoft.mavala.service.DurgCampaignService;
import com.asdsoft.mavala.service.ImageEditService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Controller
@SecurityRequirement(name = "google-jwt")
public class DurgCampaignController extends BaseController {
    private final static long CACHE_TIME = 24 * 10;
    @Autowired
    private DurgCampaignService durgCampaignService;
    @Autowired
    private ImageEditService imageEditService;

    @Value("${durg.campaign}")
    private boolean isDurgCampaignActive;

    @GetMapping("/durg/campaign")
    @ResponseBody
    public DurgCampaignData durgCampaign() {
        if(isDurgCampaignActive) {
            return durgCampaignService.campaignData(getUserData());
        }
        return new DurgCampaignData(false, false, false, false, null, new ArrayList<>());
    }

    @GetMapping("/not/login/durg/campaign/ready/certificate")
    @ResponseBody
    public DurgCampaignData durgCampaignReadyCertificateAPI() {
        return durgCampaignService.campaignDataReadyCertificate();
    }

    @GetMapping("/not/login/image/test")
    @ResponseBody
    public ResponseEntity<byte[]> durgCampaignReadyImageTest() throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(imageEditService.editImage("Amey", "deshpande"), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/durg/campaign")
    @ResponseBody
    public DurgCampaignData durgCampaign(@RequestBody DurgCampaignRequest durgCampaignRequest) {
        return durgCampaignService.submitEntry(getUserData(), durgCampaignRequest);
    }

    @PostMapping(value = "/durg/campaign/image/upload/{userCampaignId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ResponseBody
    public DurgCampaignData durgCampaignUploadImage(@PathVariable String userCampaignId, @RequestParam("photo") MultipartFile[] image) {
        return durgCampaignService.uploadImage(getUserData(), userCampaignId, image);
    }

    @GetMapping(value = "/durg/campaign/certificate")
    public ResponseEntity<byte[]> durgCampaignCertificate() throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Content-Type", "image/webp");
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(CACHE_TIME, TimeUnit.HOURS)).headers(httpHeaders).body(durgCampaignService.getUserCertificate(getUserData()));
    }

    @GetMapping(value = "/durg/campaign/user/images")
    @ResponseBody
    public UserCampaignImages durgCampaignUserImages() {
        return durgCampaignService.getUserImages(getUserData());
    }

    @GetMapping(value = "/durg/campaign/user/images/{imageId}")
    public ResponseEntity<byte[]> durgCampaignUserImages(@PathVariable Long imageId) {
        return ResponseEntity.ok().cacheControl(CacheControl.maxAge(CACHE_TIME, TimeUnit.HOURS)).contentType(MediaType.IMAGE_PNG).body(durgCampaignService.getUserImages(getUserData(), imageId));
    }
}
