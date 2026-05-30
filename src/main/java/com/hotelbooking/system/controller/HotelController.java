package com.hotelbooking.system.controller;


import com.hotelbooking.system.Entity.Hotel;
import com.hotelbooking.system.Service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class HotelController {
    @Autowired
    private HotelService hotelService;

    @GetMapping("/admin/add-hotel")
    public String addHotelPage(Model model) {
        model.addAttribute("hotel", new Hotel());
        return "add-hotel";
    }

    @PostMapping("/admin/save-hotel")
    public String saveHotel(@ModelAttribute Hotel hotel) {
        hotelService.saveHotel(hotel);
        return "redirect:/hotels";

    }

    @GetMapping("/hotels")

    public String viewHotels(Model model) {
        model.addAttribute("hotels",
                hotelService.getAllHotels());
        return "hotel-list";
    }

    @GetMapping("/admin/delete-hotel/{id}")
    public String deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
        return "redirect:/hotels";

    }

    @GetMapping("/search")
    public String searchHotels(@RequestParam String city,
                               Model model) {

        model.addAttribute("hotels",
                hotelService.searchByCity(city));

        return "hotel-list";
    }

}
