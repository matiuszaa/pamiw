package com.example.mwo.app.controller;

import java.util.List;

import com.example.mwo.app.dto.ParkingDto;
import com.example.mwo.app.dto.ReservationDto;
import com.example.mwo.app.entity.Parking;
import com.example.mwo.app.entity.ReservedParking;
import com.example.mwo.app.service.ParkingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
public class ParkingController {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private ParkingService parkingService;

    @RequestMapping("/menu")
    public String parkingMenu(Model theModel) {
        return "home.html";
    }

    @RequestMapping("/parkings")
    @PreAuthorize("hasRole('USER')")
    public String listParking(Model theModel) {
        List<ParkingDto> theParkings = parkingService.getParkingList();

        theModel.addAttribute("parkings", theParkings);

        return "parkings.html";
    }

    @RequestMapping("/reserve/page/{pageNum}")
    public ModelAndView reserveParkingSpot(Model model,
           @PathVariable(name = "pageNum") int pageNum, @RequestParam("sortField") String sortField,
           @RequestParam("sortDir") String sortDir) {
        Page<Parking> parkPage = parkingService.findParkingsWithOpenSpaces(pageNum, sortField, sortDir);
        List<Parking> parking = parkPage.getContent();
        ModelAndView modelAndView = new ModelAndView("reserve.html");
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", parkPage.getTotalPages());
        model.addAttribute("totalMsgs", parkPage.getTotalElements());
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");
        modelAndView.addObject("parkings", parking);
        return modelAndView;
    }

    @RequestMapping("/getParkingSpot")
    public String getParkingSpot(@RequestParam String adress) {
        ParkingDto parkingDto = new ParkingDto();
        parkingDto.setAdress(adress);
        parkingService.reserveParkingSpot(parkingDto);

        return "redirect:/menu";
    }

    @RequestMapping("/reservation")
    public String getReservationList(Model theModel) {
        List<ReservationDto> reservations = parkingService.getReservations();
        theModel.addAttribute("reservations", reservations);
        return "reservation.html";
    }

    @RequestMapping("/release")
    public String releaseSpace(@RequestParam String spaceSignature,@RequestParam String adress) {
        parkingService.releaseSpace(spaceSignature, adress);
        return "redirect:/menu";
    }
}
