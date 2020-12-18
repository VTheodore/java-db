package com.vezenkov.gamestore.domain.dtos;

import com.vezenkov.gamestore.domain.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;

    private String email;

    private String password;

    private String fullName;

    private Role role;
}
