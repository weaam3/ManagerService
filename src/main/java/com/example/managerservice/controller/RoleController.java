package com.example.managerservice.controller;

import com.example.managerservice.model.Role;
import com.example.managerservice.service.Implementation.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;
    public RoleController(RoleService roleService){
        this.roleService = roleService;
    }

    @RequestMapping("/getAll")
    public String getAll(Model model){
        List<Role> rolesList = roleService.getAll();

        model.addAttribute( "roles", rolesList);

        return "roles";
    }
    @RequestMapping("/getById")
    public String getById(long roleId, Model model){
        Role role = roleService.getById(roleId);

        model.addAttribute( "role", role);

        return "role";
    }
    @RequestMapping("/save")
    public String save(Role role, Model model){
        Role savedRole = roleService.save(role);

        model.addAttribute( "role", role);

        return "role";
    }
}
