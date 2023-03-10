package ru.clevertec.cheque.entity;

import java.util.Objects;

public class Position {
    private final int quantity;
    private final Product product;
    private final double cost;
    private double totalCost;

    public Position(int quantity, Product product) {
        this.quantity = quantity;
        this.product = product;
        this.cost = calculate();
        this.totalCost = this.cost;
    }

    public int getQuantity() {
        return quantity;
    }

    public Product getProduct() {
        return product;
    }

    public double getCost() {
        return cost;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return quantity == position.quantity
                && Double.compare(position.cost, cost) == 0
                && Double.compare(position.totalCost, totalCost) == 0
                && product.equals(position.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, product, cost, totalCost);
    }

    @Override
    public String toString() {
        return "Position{" +
                "quantity=" + quantity +
                ", product=" + product +
                ", cost=" + cost +
                ", discountedCost=" + totalCost +
                '}';
    }

    private double calculate() {
        return quantity * product.getPrice();
    }
}
