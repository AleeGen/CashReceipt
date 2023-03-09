package ru.clevertec.cheque.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.cheque.dao.impl.ProductDAO;
import ru.clevertec.cheque.entity.Product;
import ru.clevertec.cheque.service.EntityService;

import java.util.List;

@Service
public class ProductService implements EntityService<Product> {
    @Autowired
    private ProductDAO productDao;

    @Transactional
    @Override
    public List<Product> getAll() {
        return productDao.getAll();
    }

    @Transactional
    @Override
    public Product getById(int id) {
        return productDao.getById(id);
    }

    @Transactional
    @Override
    public void save(Product product) {
        productDao.save(product);
    }

    @Transactional
    @Override
    public void delete(int id) {
        productDao.delete(id);
    }

    @Transactional
    @Override
    public void update(Product product) {
        productDao.update(product);
    }

}
