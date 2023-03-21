package ru.clevertec.cheque.entity;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Human {

    @Min(0)
    private int id;

    @NotBlank
    @Pattern(regexp = "^(male)|(female)$")
    private String gender;

    @Min(0)
    @Max(150)
    private int age;

    @NotBlank
    @Pattern(regexp = "^\\w+$")
    private String name;

    private boolean higherEducation;
}
