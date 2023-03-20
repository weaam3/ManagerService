package com.example.managerservice.service.Interfaces;


import com.example.managerservice.model.User;

public interface IUserService {
    User save(User user);
    User getByUserName(String username,String password);
    User getUsernameById(long userId);
}
