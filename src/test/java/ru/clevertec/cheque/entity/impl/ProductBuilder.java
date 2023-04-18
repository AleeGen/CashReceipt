package ru.clevertec.cheque.entity.impl;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.cheque.entity.EntityBuilder;
import ru.clevertec.cheque.entity.Product;

@With
@AllArgsConstructor
@NoArgsConstructor(staticName = "aProduct")
public class ProductBuilder implements EntityBuilder<Product> {

    private int id = 0;
    private String description = "description";
    private double price = 11.1;
    private boolean promotional = false;

    @Override
    public Product build() {
        final Product product = new Product();
        product.setId(id);
        product.setDescription(description);
        product.setPrice(price);
        product.setPromotional(promotional);
        return product;
    }

}