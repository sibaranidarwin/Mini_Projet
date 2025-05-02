package com.example.mini_project.dto.auth;


import java.util.List;

import com.example.mini_project.model.Role;
import lombok.Data;

@Data
public class UserResponseDTO {

    private Integer id;
    private String username;
    private String email;
    List<Role> appUserRoles;

}