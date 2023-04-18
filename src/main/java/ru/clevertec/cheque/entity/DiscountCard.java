package ru.clevertec.cheque.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="discount_card")
public class DiscountCard {

    @Id
    @Column(name = "number")
    private int number;

    @Min(0)
    @Max(100)
    @Column(name = "discount")
    private double discount;

}