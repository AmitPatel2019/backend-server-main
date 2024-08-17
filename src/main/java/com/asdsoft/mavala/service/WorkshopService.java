package com.asdsoft.mavala.service;

import com.asdsoft.mavala.data.WorkshopData;
import com.asdsoft.mavala.data.WorkshopList;
import com.asdsoft.mavala.entity.UserMawala;
import com.asdsoft.mavala.entity.Workshop;
import com.asdsoft.mavala.repository.WorkShopDataRepository;
import com.asdsoft.mavala.repository.WorkshopRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class WorkshopService {
    private final WorkshopRepository workshopRepository;

    private final WorkShopDataRepository workShopDataRepository;
    private final Random random = new Random();

    public WorkshopService(WorkshopRepository workshopRepository, WorkShopDataRepository workShopDataRepository) {
        this.workshopRepository = workshopRepository;
        this.workShopDataRepository = workShopDataRepository;
    }

    public Workshop addWorkShop(WorkshopData workshopData, UserMawala userMawala) {
        Workshop workshop = new Workshop();
        workshop.setEmailAddress(workshopData.getEmailAddress());
        workshop.setAttendeeName(workshopData.getAttendeeName());
        workshop.setTimeSlot(workshopData.getTimeSlot());
        workshop.setAge(workshopData.getAge());
        workshop.setUserMawala(userMawala);
        workshop.setBooking_id(String.format("MW-%09d", random.nextInt(999999999)));
        workshop.setWorkshopId(workshopData.getWorkshopId());
        return workshopRepository.save(workshop);
    }

    public List<WorkshopList> getWorkShopList(UserMawala userMawala) {
        Map<Long, Workshop> workshopMap = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        workshopRepository.findAll().stream().filter(workshop -> workshop.getUserMawala().getFirebaseId().equals(userMawala.getFirebaseId())).forEach(workshop -> workshopMap.put(workshop.getWorkshopId(), workshop));
        return workShopDataRepository.findAll().stream().filter(workShopData -> {
            try {
                Date date = formatter.parse(workShopData.getDay());
                return date.after(new Date());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }).map(workShopData -> {
            if (workshopMap.containsKey(workShopData.getId()))
                return new WorkshopList(workShopData, workshopMap.get(workShopData.getId()));
            return new WorkshopList(workShopData);
        }).collect(Collectors.toList());
    }

    public List<WorkshopList> getUserWorkShopList(UserMawala userMawala) {
        Map<Long, Workshop> workshopMap = new HashMap<>();
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);

        workshopRepository.findAll().stream().filter(workshop -> workshop.getUserMawala().getFirebaseId().equals(userMawala.getFirebaseId())).forEach(workshop -> workshopMap.put(workshop.getWorkshopId(), workshop));
        return workShopDataRepository.findAll().stream().filter(workShopData -> workshopMap.containsKey(workShopData.getId())).filter(workShopData -> {
            try {
                Date date = formatter.parse(workShopData.getDay());
                return date.after(new Date());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }).map(workShopData -> new WorkshopList(workShopData, workshopMap.get(workShopData.getId()))).collect(Collectors.toList());
    }
}
