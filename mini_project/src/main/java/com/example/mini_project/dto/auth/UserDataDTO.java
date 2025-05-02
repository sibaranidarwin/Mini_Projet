package com.example.mini_project.dto.auth;


import java.util.List;

import com.example.mini_project.model.Role;
import com.example.mini_project.model.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDataDTO {

    private String username;
    private String email;
    private String password;
    List<Role> appUserRoles;

}
