package ru.clevertec.cheque.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Organization {
    private String name;
    private String address;
    private String email;
    private String telephone;

}