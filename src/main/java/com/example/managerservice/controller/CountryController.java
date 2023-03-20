package com.example.managerservice.controller;


import com.example.managerservice.model.Country;
import com.example.managerservice.service.Interfaces.ICountryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller()
@RequestMapping("/countries")
public class CountryController {
    private final ICountryService countryService;
    public CountryController(ICountryService countryService){
        this.countryService = countryService;
    }
    @GetMapping
    @RequestMapping("/all")
    public String getAll(Model model) {
        List<Country> countryList = countryService.getAll();

        model.addAttribute("countries", countryList);

        return "hotel";
    }
    @PostMapping
    @RequestMapping("/save")
    public String save(Country country, Model model) {
        Country savedCountry = countryService.save(country);

        model.addAttribute("country", savedCountry);

        return "country";
    }
}
