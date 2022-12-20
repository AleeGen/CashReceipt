package helper;

import entity.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class HelperTest {

    public static CashReceipt getCashReceipt() {
        Optional<DiscountCard> discountCard = Optional.of(new DiscountCard((short) 1111, 20));
        List<Position> positions = new ArrayList<>() {{
            add(new Position(7, new Product(1, "product1", 1.35, true)));
            add(new Position(8, new Product(2, "product2", 2.47, false)));
            add(new Position(4, new Product(3, "product3", 5.10, true)));
        }};
        Organization organization = new Organization("organization","address","email","phone");
        Date date = new Date();
        date.setTime(1);
        return new CashReceipt.CashReceiptBuilder(positions)
                .addDiscountCard(discountCard)
                .addOrganization(organization)
                .addDate(date)
                .build();
    }
}
