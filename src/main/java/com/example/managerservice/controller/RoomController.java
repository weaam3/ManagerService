package com.example.managerservice.controller;

import com.example.managerservice.model.Room;
import com.example.managerservice.model.RoomType;
import com.example.managerservice.service.Interfaces.IRoomService;
import com.example.managerservice.service.Interfaces.IRoomTypeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/room")
public class RoomController {
    private final IRoomService roomService;
    private final IRoomTypeService roomTypeService;

    public RoomController(IRoomService roomService, IRoomTypeService roomTypeService) {
        this.roomService = roomService;
        this.roomTypeService = roomTypeService;
    }

    @PostMapping(path = "/save")
    public String saveRoom(@RequestParam("hotelId") long hotelId, @RequestParam("roomNo") String roomNo, @RequestParam("roomTypeId") long roomType, @RequestParam("bedCount") int bedCount, Model model) {
        Room savedRoom = roomService.save(new Room(-1, hotelId, roomNo, roomType, bedCount));

        model.addAttribute("room", savedRoom);
        return "room";
    }

    @GetMapping(path = "/getById")
    public String getRoom(@RequestParam long roomId, Model model) {
        Room roomFromDb = roomService.getById(roomId);

        model.addAttribute("room", roomFromDb);

        return "room";
    }

    @GetMapping("/editView")
    public String updateView(@RequestParam("roomId") long roomId, Model model) {
        Room roomFromDb = roomService.getById(roomId);
        model.addAttribute("room", roomFromDb);

        List<RoomType> roomTypes = roomTypeService.getAllRoomTypes();
        model.addAttribute("roomTypes", roomTypes);

        return "updateRoom";
    }

    @PostMapping("/edit")
    public String update(@RequestParam("roomId") long roomId, @RequestParam("roomNo") String roomNo, @RequestParam("roomTypeId") long roomType, @RequestParam("bedCount") int bedCount, Model model) {
        Room roomFromDb = roomService.getById(roomId);
        roomFromDb.setNumber(roomNo);
        roomFromDb.setTypeId(roomType);
        roomFromDb.setBedCount(bedCount);

        roomFromDb = roomService.updateRoom(roomFromDb);

        model.addAttribute("room", roomFromDb);
        return "room";
    }

    @PostMapping(path = "/delete")
    public String deleteByIdAndManagerID(@RequestParam long roomId) {
        roomService.deleteById(roomId);

        return "redirect:/";
    }
}
