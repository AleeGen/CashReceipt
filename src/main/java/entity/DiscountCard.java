package entity;

public class DiscountCard {
    private short number;
    private double discount;

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
    public String toString() {
        return "DiscountCard{" +
                "number=" + number +
                ", discount=" + discount +
                '}';
    }
}
