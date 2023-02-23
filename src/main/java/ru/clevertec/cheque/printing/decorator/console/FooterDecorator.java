package ru.clevertec.cheque.printing.decorator.console;

import ru.clevertec.cheque.entity.CashReceipt;
import ru.clevertec.cheque.printing.Printer;

public class FooterDecorator extends PrinterDecorator {
    private static final String COST = "Cost: ";
    private static final String DISCOUNT = "\r\nDiscount: ";
    private static final String TOTAL_COST = "\r\nTOTAL COST: ";

    public FooterDecorator(Printer<CashReceipt> printer) {
        super(printer);
    }

    @Override
    public String print(CashReceipt cashReceipt) {
        double cost = cashReceipt.getCost();
        double totalCost = cashReceipt.getTotalCost();
        StringBuilder footer = new StringBuilder().append(SEPARATOR);
        if (totalCost != cost) {
            double discount = cost - totalCost;
            footer.append(COST)
                    .append(String.format(FORMAT_NUMBER, cost))
                    .append(DISCOUNT)
                    .append(String.format(FORMAT_NUMBER, discount));
        }
        footer.append(TOTAL_COST).append(String.format(FORMAT_NUMBER, totalCost));
        String past = super.print(cashReceipt);
        return past + footer;
    }
}