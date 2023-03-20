package com.example.managerservice.controller;


import com.example.managerservice.model.Country;
import com.example.managerservice.model.Hotel;
import com.example.managerservice.model.Room;
import com.example.managerservice.model.RoomType;
import com.example.managerservice.service.Interfaces.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
@RequestMapping({"/hotels", "/"})
public class HotelController {
    private final IHotelService hotelService;
    private final IUserService userService;
    private final IRoomService roomService;
    private final ICountryService countryService;
    private final IRoomTypeService roomTypeService;

    public HotelController(IHotelService hotelService, IUserService userService, IRoomService roomService, ICountryService countryService, IRoomTypeService roomTypeService) {
        this.hotelService = hotelService;
        this.userService = userService;
        this.roomService = roomService;
        this.countryService = countryService;
        this.roomTypeService = roomTypeService;
    }

    @PostMapping(path = "/save")
    public String save(@RequestParam("countryId") long countryId, @RequestParam("hotelName") String hotelName, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long managerId = userService.getByUserName(username, "").getId();
        Hotel hotel = new Hotel(-1, hotelName, managerId, countryId, "");
        Hotel SavedHotel = hotelService.save(hotel);

        model.addAttribute("hotel", SavedHotel);

        return "hotel";
    }


    @GetMapping(path = {"/AllByManagerID", "/"})
    public String getAllByManagerID(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long managerId = userService.getByUserName(username, "").getId();

        List<Hotel> hotels = hotelService.getAllByManagerID(managerId);

        model.addAttribute("hotels", hotels);

        return "hotels";
    }

    @GetMapping(path = "/ByHotelIdAndManagerId")
    public String getByHotelIdAndManagerId(@RequestParam long hotelId, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long managerId = userService.getByUserName(username, "").getId();

        Hotel hotel = hotelService.getByHotelIdAndManagerId(hotelId, managerId);
        List<Room> rooms = roomService.getAllByHotelIdAndManagerId(hotelId, managerId);

        model.addAttribute("hotel", hotel);
        model.addAttribute("rooms", rooms);

        return "hotel";
    }

    @GetMapping("/editView")
    public String updateView(@RequestParam("hotelId") long hotelId, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long managerId = userService.getByUserName(username, "").getId();

        Hotel hotel = hotelService.getByHotelIdAndManagerId(hotelId, managerId);
        List<Country> countries = countryService.getAll();

        model.addAttribute("countries", countries);
        model.addAttribute("hotel", hotel);

        return "updateHotel";
    }

    @PostMapping("/edit")
    public String update(@RequestParam("hotelId") long hotelId, @RequestParam("hotelName") String hotelName, @RequestParam("countryId") long countryId, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long managerId = userService.getByUserName(username, "").getId();

        Hotel hotel = hotelService.getByHotelIdAndManagerId(hotelId, managerId);

        hotel.setName(hotelName);
        hotel.setCountryId(countryId);

        hotel = hotelService.update(hotel);

        model.addAttribute("hotel", hotel);
        return "hotel";
    }

    @PostMapping(path = "/delete")
    public String deleteByIdAndManagerID(@RequestParam long hotelId, Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long managerId = userService.getByUserName(username, "").getId();

        hotelService.deleteByIdAndManagerID(hotelId, managerId);

        return "redirect:/";
    }

    @GetMapping(path = "/newHotel")
    public String newHotel(Model model) {
        List<Country> countries = countryService.getAll();
        model.addAttribute("countries", countries);
        return "newHotel";
    }

    @GetMapping(path = "/newRoom")
    public String newRoom(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        long managerId = userService.getByUserName(username, "").getId();

        List<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
        model.addAttribute("roomTypes", roomTypes);

        List<Hotel> hotels = hotelService.getAllByManagerID(managerId);
        model.addAttribute("hotels", hotels);
        return "newRoom";
    }
}
