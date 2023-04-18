package ru.clevertec.cheque.service.impl;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.cheque.dao.ProductRepository;
import ru.clevertec.cheque.entity.DiscountCard;
import ru.clevertec.cheque.entity.Product;
import ru.clevertec.cheque.entity.impl.ProductBuilder;
import ru.clevertec.cheque.entity.CashReceiptProvider;
import ru.clevertec.cheque.exception.ProductException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    private static List<Product> products;
    @Mock
    private ProductRepository rep;
    @Mock
    private Page<DiscountCard> page;
    @InjectMocks
    private ProductService service;

    @BeforeAll
    static void init() {
        products = CashReceiptProvider.getProducts().values().stream().toList();
    }

    @Test
    void checkGetAllShouldReturnExpected() {
        int nPage = 0;
        int size = 20;
        doReturn(products).when(page).getContent();
        doReturn(page).when(rep).findAll(any(PageRequest.class));
        List<Product> actual = service.findAll(nPage, size);
        assertThat(actual).isEqualTo(products);
    }

    @Nested
    class checkGetById {
        @Test
        void ShouldExecute() {
            int id = 1;
            doReturn(Optional.of(ProductBuilder.aProduct().build())).when(rep).findById(id);
            service.findById(id);
            verify(rep).findById(id);
        }

        @Test
        void ShouldThrowProductException() {
            int id = -1;
            doReturn(Optional.empty()).when(rep).findById(id);
            assertThrows(ProductException.class, () -> service.findById(id));
        }
    }

    @Test
    void checkSaveShouldExecute() {
        Product product = products.get(0);
        service.save(product);
        verify(rep).save(product);
    }

    @Test
    void checkUpdateShouldExecute() {
        Product product = products.get(0);
        service.update(product);
        verify(rep).save(product);
    }

    @Test
    void checkDeleteShouldExecute() {
        int id = 1;
        service.deleteById(id);
        verify(rep).deleteById(id);
    }

}