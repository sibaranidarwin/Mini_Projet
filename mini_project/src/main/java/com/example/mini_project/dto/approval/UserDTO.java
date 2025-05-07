package com.example.mini_project.dto.approval;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {
    private String username;

    public UserDTO(Long id, String username) {}

    public UserDTO(String username) {
        this.username = username;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

