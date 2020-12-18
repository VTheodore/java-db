package com.vezenkov.productshopxml.data.dtos.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.xml.bind.annotation.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "user")
@XmlAccessorType(XmlAccessType.FIELD)
public class UserNameAndAgeDto {
    @Length(max = 60, message = "First name must be max 60 characters long.")
    @XmlAttribute(name = "first-name")
    private String firstName;

    @NotNull(message = "User last name must not be null.")
    @Length(min = 3, max = 60, message = "User last name must be between 3 and 60 characters long.")
    @XmlAttribute(name = "last-name")
    private String lastName;

    @PositiveOrZero(message = "Age must be positive or zero.")
    @XmlAttribute(name = "age")
    private int age;
}
