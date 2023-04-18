package ru.clevertec.cheque.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.cheque.entity.DiscountCard;

public interface CardRepository extends JpaRepository<DiscountCard, Integer> {
}