package com.asdsoft.mavala.web;

import com.asdsoft.mavala.data.*;
import com.asdsoft.mavala.entity.*;
import com.asdsoft.mavala.service.*;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@Controller
@SecurityRequirement(name = "google-jwt")
public class MawalaController extends BaseController {
    @Autowired
    private PodcastService podcastService;

    @Autowired
    private AudioService audioService;

    @Autowired
    private WarriorService warriorService;

    @Autowired
    private UserService userService;

    @Autowired
    private WorkshopService workshopService;

    @Autowired
    private WarStoriesService warStoriesService;

    @Autowired
    private DashboardService dashboardService;

    @Value("${lang.list}")
    private String[] langList;

    @Value("${podcast.filters}")
    private String[] podcastFilters;

    @GetMapping("/podcast/groups/{filter}")
    @ResponseBody
    public List<PodcastGroups> getAllPodcastGroupList(@PathVariable String filter) {
        return podcastService.getAllPodCastGroups();
    }

    @GetMapping("/podcast/{lan}/{podcast_group}")
    @ResponseBody
    public List<Podcast> getAllPodcastList(@PathVariable String lan, @PathVariable String podcast_group) {
        return podcastService.getAllPodCast(lan, podcast_group);
    }

    @GetMapping("/footsteps/{lan}")
    @ResponseBody
    public List<Audio> getAllFootSteps(@PathVariable String lan) {
        return audioService.getAllAudioList(lan);
    }

    @GetMapping("/warriors/{lan}")
    @ResponseBody
    public List<Warrior> getAllWarriors(@PathVariable String lan) {
        return warriorService.getAllWarriors(lan);
    }

    @GetMapping("/warriors/{warrior_name}/audio/{lan}")
    @ResponseBody
    public List<Audio> getAllWarriorsAudio(@PathVariable String warrior_name, @PathVariable String lan) {
        return warriorService.getAllAudioForWarrior(warrior_name, lan);
    }

    @PostMapping("/board/info")
    @ResponseBody
    public BoardInfo updateBoardInfo(@RequestBody BoardInfo boardInfo) {
        return userService.updateBoardInfo(getUserData(), boardInfo);
    }

    @GetMapping("/lang/list")
    @ResponseBody
    public LangList getLangList() {
        return new LangList(langList);
    }

    @GetMapping("/podcast/filters")
    @ResponseBody
    public PodcastFilters getPodCastFilters() {
        return new PodcastFilters(podcastFilters);
    }

    @PostMapping("/workshop")
    @ResponseBody
    public WorkshopData addWorkshopData(@RequestBody WorkshopData workshopData) {
        workshopService.addWorkShop(workshopData, getUserData());
        return workshopData;
    }

    @GetMapping("/workshop")
    @ResponseBody
    public List<WorkshopList> getWorkshopData() {
        return workshopService.getWorkShopList(getUserData());
    }

    @GetMapping("/toggle/premium")
    @ResponseBody
    public String togglePremium() {
        userService.togglePremium(getUser());
        return "Done";
    }


    @GetMapping("/user/workshop")
    @ResponseBody
    public List<WorkshopList> getUserWorkshopData() {
        return workshopService.getUserWorkShopList(getUserData());
    }

    @GetMapping("/war-stories")
    @ResponseBody
    public List<WarStories> getWarStories() {
        return warStoriesService.getAllWarStories();
    }

    @GetMapping("/mawala-demo")
    @ResponseBody
    public List<WarStories> getMawalaDemoVideos() {
        return warStoriesService.getAllWarStories();
    }

    @GetMapping("/dashboard")
    @ResponseBody
    public Dashboard getDashboard() {
        return new Dashboard(dashboardService.getDashBoardCards(), dashboardService.getDynamicCard(getUser()));
    }

    @GetMapping("/dashboard/dynamic/card/{id}")
    @ResponseBody
    public List<DynamicCardData> getDashboardData(String id) {
        return  dashboardService.getDynamicCardData(id);
    }

}
