package entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name="discount_card")
public class DiscountCard {

    @Id
    @Column(name = "number")
    private short number;

    @Column(name = "discount")
    private double discount;

    public DiscountCard(){}

    public DiscountCard(short number, double discount) {
        this.number = number;
        this.discount = discount;
    }

    public short getNumber() {
        return number;
    }

    public void setNumber(short number) {
        this.number = number;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DiscountCard that = (DiscountCard) o;
        return number == that.number && Double.compare(that.discount, discount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, discount);
    }

    @Override
    public String toString() {
        return "DiscountCard{" +
                "number=" + number +
                ", discount=" + discount +
                '}';
    }
}
