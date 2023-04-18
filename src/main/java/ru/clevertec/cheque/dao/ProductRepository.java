package ru.clevertec.cheque.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.cheque.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}