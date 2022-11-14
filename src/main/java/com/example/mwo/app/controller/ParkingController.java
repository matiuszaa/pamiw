package com.example.mwo.app.controller;

import java.util.List;

import com.example.mwo.app.dto.ParkingDto;
import com.example.mwo.app.dto.ReservationDto;
import com.example.mwo.app.entity.Parking;
import com.example.mwo.app.entity.ReservedParking;
import com.example.mwo.app.service.ParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/parking")
public class ParkingController {

    @Autowired
    private ParkingService parkingService;

    @RequestMapping("/menu")
    public String parkingMenu(Model theModel) {
        return "parking-menu-for-users";
    }

    @GetMapping("/list")
    public List<ParkingDto> listParking(Model theModel) {

        List<ParkingDto> theParkings = parkingService.getParkingList();

        theModel.addAttribute("parkings", theParkings);

        return theParkings;
    }

    @RequestMapping("/reserve")
    public List<String> reserveParkingSpot(Model theModel) {
        Parking theParking = new Parking();
        List<String> parkings = parkingService.showAvailableParkings();
        theModel.addAttribute("theParking", theParking);
        theModel.addAttribute("parkingsy", parkings);

        return parkings;
    }

    @PostMapping("/getParkingSpot")
    public String getParkingSpot(@ModelAttribute("theParking") ParkingDto theParking) {
        parkingService.reserveParkingSpot(theParking);

        return "Spot Reserved";
    }

    @GetMapping("/reserve-list")
    public List<ReservationDto> getReservationList(Model theModel) {
        List<ReservationDto> reservations = parkingService.getReservations();
        ReservedParking reserved = new ReservedParking();
        theModel.addAttribute("reservations", reservations);
        theModel.addAttribute("reserved", reserved);
        return reservations;
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam("tempReservations") String reserved) {
        String[] list = reserved.split(";");
        parkingService.releaseSpace(list);
        return "deleted";
    }
}
