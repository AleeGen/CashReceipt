package entity;

import util.generation.NumberGenerator;

import java.util.Date;
import java.util.Map;

public class CashReceipt {

    private long number;
    private Organization organization;
    private Date date;
    private Map<Integer, Product> products;
    private double totalCost;

    public CashReceipt() {
        number = NumberGenerator.generate();
    }

    public long getNumber() {
        return number;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Map<Integer, Product> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Product> products) {
        this.products = products;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    @Override
    public String toString() {
        return "CashReceipt{" +
                "number=" + number +
                ", organization=" + organization +
                ", date=" + date +
                ", products=" + products +
                ", totalCost=" + totalCost +
                '}';
    }

    public static class CashReceiptBuilder {

        private Organization organization;
        private Date date;
        private Map<Integer, Product> products;
        private double totalCost;

        public CashReceiptBuilder addOrganization(Organization organization) {
            this.organization = organization;
            return this;
        }

        public CashReceiptBuilder addDate(Date date) {
            this.date = date;
            return this;
        }

        public CashReceiptBuilder addProducts(Map<Integer, Product> products) {
            this.products = products;
            return this;
        }

        public CashReceiptBuilder addTotalCost(double totalCost) {
            this.totalCost = totalCost;
            return this;
        }

        public CashReceipt build() {
            CashReceipt cashReceipt = new CashReceipt();
            cashReceipt.setOrganization(organization);
            cashReceipt.setDate(date);
            cashReceipt.setProducts(products);
            return cashReceipt;
        }

    }
}
