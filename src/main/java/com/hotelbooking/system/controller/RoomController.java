package com.hotelbooking.system.controller;

import com.hotelbooking.system.Entity.Hotel;
import com.hotelbooking.system.Entity.Rooms;
import com.hotelbooking.system.Repository.HotelRepository;
import com.hotelbooking.system.Service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private HotelRepository hotelRepository;


    @GetMapping("/admin/add-room/{hotelId}")
    public String addRoomPage(@PathVariable Long hotelId, Model model) {
        Rooms room = new Rooms();
        
        Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
        room.setHotel(hotel);


        model.addAttribute("room", room);
        return "add-room";
    }

    @PostMapping("/admin/save-room")
    public String saveRoom(@ModelAttribute Rooms room, @RequestParam("hotelId") Long hotelId) {
        System.out.println("DEBUG: Attempting to save room " + room.getRoomNumber() + " for hotel ID: " + hotelId);
        try {
            Hotel hotel = hotelRepository.findById(hotelId).orElse(null);
            if (hotel != null) {
                // Manually create a new object to avoid any "binding" or "proxy" issues
                Rooms newRoom = new Rooms();
                
                // Auto-generate room number in order (1, 2, 3...) based on current hotel rooms
                int currentRoomsCount = roomService.getRoomsByHotel(hotelId).size();
                newRoom.setRoomNumber(String.valueOf(currentRoomsCount + 1));
                
                newRoom.setRoomType(room.getRoomType());
                newRoom.setPrice(room.getPrice());
                newRoom.setAvailable(true); // Default to available
                newRoom.setTotalRooms(1);   // Explicitly set to satisfy database constraint
                newRoom.setHotel(hotel);

                roomService.saveRoom(newRoom);
                System.out.println("DEBUG: Room saved successfully!");
                return "redirect:/rooms/" + hotelId;
            }
            System.out.println("DEBUG: Hotel NOT FOUND for ID: " + hotelId);
            return "redirect:/hotels?error=HotelNotFound";
        } catch (Exception e) {
            System.out.println("DEBUG: ERROR SAVING ROOM: " + e.getMessage());
            e.printStackTrace(); 
            return "redirect:/admin/add-room/" + hotelId + "?error=DatabaseError";
        }
    }


    @GetMapping("/rooms/{hotelId}")
    public String viewRooms(@PathVariable Long hotelId, Model model) {
        model.addAttribute("rooms", roomService.getRoomsByHotel(hotelId));
        model.addAttribute("hotelId", hotelId); // Required for the "Add Another Room" button
        return "room-list";
    }
}
