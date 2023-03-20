package com.example.managerservice.service.Interfaces;


import com.example.managerservice.model.Country;

import java.util.List;

public interface ICountryService {
    List<Country> getAll();
    Country save(Country country);
    Country getCountryById(long id);
}
