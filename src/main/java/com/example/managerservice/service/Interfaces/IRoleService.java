package com.example.managerservice.service.Interfaces;


import com.example.managerservice.model.Role;

import java.util.List;

public interface IRoleService {
    List<Role> getAll();

    Role getById(long roleId);

    Role save(Role role);
}
