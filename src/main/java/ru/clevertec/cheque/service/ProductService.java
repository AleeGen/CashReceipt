package ru.clevertec.cheque.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.clevertec.cheque.dao.ProductDao;
import ru.clevertec.cheque.entity.Product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {
    @Autowired
    private ProductDao productDap;

    public Map<Integer, Product> getAll() {
        Map<Integer, Product> productMap = new HashMap<>();
        List<Product> products = productDap.getAll();
        for (Product product : products) {
            productMap.put(product.getId(), product);
        }
        return productMap;
    }
}
