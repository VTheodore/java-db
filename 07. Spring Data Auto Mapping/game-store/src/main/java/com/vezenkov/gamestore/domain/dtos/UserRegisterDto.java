package com.vezenkov.gamestore.domain.dtos;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDto {
    @NotNull
    @Pattern(regexp = "[^@]+@[^.]+\\..+", message = "The provided email does not meet the requirements!")
    private String email;

    @Size(min = 6, max = 32, message = "Password length not in bounds!")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,32}", message = "Password does not meet the requirements!")
    private String password;

    @NotNull
    private String fullName;
}
