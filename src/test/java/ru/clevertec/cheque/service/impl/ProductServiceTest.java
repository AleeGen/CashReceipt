package ru.clevertec.cheque.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.cheque.dao.impl.ProductDAO;
import ru.clevertec.cheque.entity.Product;
import ru.clevertec.cheque.provider.CashReceiptProvider;
import ru.clevertec.cheque.service.impl.ProductService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private static List<Product> products;
    @Mock
    private ProductDAO dao;
    @InjectMocks
    private ProductService service;

    @BeforeAll
    static void init() {
        products = CashReceiptProvider.getProducts().values().stream().toList();
    }

    @Test
    void checkGetAllShouldReturnExpected() {
        doReturn(products).when(dao).getAll();
        List<Product> actual = service.getAll();
        assertThat(actual).isEqualTo(products);
    }

    @Test
    void checkGetByIdShouldExecute() {
        int id = 1;
        service.getById(id);
        verify(dao).getById(id);
    }

    @Test
    void checkSaveShouldExecute() {
        Product product = products.get(0);
        service.save(product);
        verify(dao).save(product);
    }

    @Test
    void checkUpdateShouldExecute() {
        Product product = products.get(0);
        service.update(product);
        verify(dao).update(product);
    }

    @Test
    void checkDeleteShouldExecute() {
        int id = 1;
        service.delete(id);
        verify(dao).delete(id);
    }

}