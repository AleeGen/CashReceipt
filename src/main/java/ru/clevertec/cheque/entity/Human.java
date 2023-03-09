package ru.clevertec.cheque.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Human {

    @Min(0)
    private int id;

    @NotBlank
    @Pattern(regexp = "^(male)|(female)$")
    private String gender;

    @Size(min = 0, max = 150)
    private int age;

    @NotBlank
    @Pattern(regexp = "^\\w+$")
    private String name;

    private boolean higherEducation;
}
