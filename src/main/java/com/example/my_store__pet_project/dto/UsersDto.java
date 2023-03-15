package com.example.my_store__pet_project.dto;

import com.example.my_store__pet_project.enums.UserRole;
import com.example.my_store__pet_project.enums.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsersDto {

    private Integer userId;

    private LocalDateTime dateAdded;

    private String name;

    private String password;

    private String email;

    private UserRole role;

    private UserStatus status;

    private List<OrderDto> orderDtoList;
}
