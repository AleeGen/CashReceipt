package ru.clevertec.cheque.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.clevertec.cheque.dao.ProductDao;
import ru.clevertec.cheque.entity.Product;
import ru.clevertec.cheque.provider.CashReceiptProvider;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private static List<Product> products;
    @Mock
    private ProductDao dao;
    @InjectMocks
    private ProductService service;

    @BeforeAll
    static void init() {
        products = CashReceiptProvider.getProducts().values().stream().toList();
    }

    @Test
    void checkGetAllShouldReturnExpected() {
        Mockito.doReturn(products).when(dao).getAll();
        Map<Integer, Product> actual = service.getAll();
        Map<Integer, Product> expected = CashReceiptProvider.getProducts();
        assertThat(actual).isEqualTo(expected);
    }

}