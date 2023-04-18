package ru.clevertec.cheque.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.cheque.cache.Cache;
import ru.clevertec.cheque.dao.ProductRepository;
import ru.clevertec.cheque.entity.Product;
import ru.clevertec.cheque.exception.ProductException;
import ru.clevertec.cheque.service.EntityService;

import java.util.List;

@Service
public class ProductService implements EntityService<Product> {

    private final ProductRepository productRep;
    @Value(value = "${pagination.page}")
    private int defaultPage;
    @Value(value = "${pagination.size}")
    private int defaultSize;

    public ProductService(ProductRepository productRep) {
        this.productRep = productRep;
    }

    @Cache
    @Transactional
    @Override
    public List<Product> findAll(Integer page, Integer size) {
        page = page == null ? defaultPage : page;
        size = size == null ? defaultSize : size;
        return productRep.findAll(PageRequest.of(page, size)).getContent();
    }

    @Cache
    @Transactional
    @Override
    public Product findById(int id) {
        return productRep.findById(id).orElseThrow(() ->
                new ProductException(String.format("Not found product with id: %d", id)));
    }

    @Cache
    @Transactional
    @Override
    public Product save(Product product) {
        return productRep.save(product);
    }

    @Cache
    @Transactional
    @Override
    public void deleteById(int id) {
        productRep.deleteById(id);
    }

    @Transactional
    @Override
    public Product update(Product product) {
        return productRep.save(product);
    }

}