package printing.decorator;

import entity.CashReceipt;
import entity.Position;
import printing.Printer;

import java.util.List;

public class BodyDecorator extends PrinterDecorator {

    private static final String FORMAT_FIELD = "%-40s";
    private static final String DISCOUNT = "\n\twith discount: \t";

    public BodyDecorator(Printer<CashReceipt> printer) {
        super(printer);
    }

    @Override
    public String print(CashReceipt cashReceipt) {
        List<Position> positions = cashReceipt.getPositions();
        StringBuilder body = new StringBuilder().append(SEPARATOR);
        for (Position position : positions) {
            body.append(position.getQuantity())
                    .append("\t")
                    .append(String.format(FORMAT_FIELD, position.getProduct().getDescription()))
                    .append(String.format(FORMAT_NUMBER, position.getProduct().getPrice()))
                    .append(TAB)
                    .append(String.format(FORMAT_NUMBER, position.getCost()));
            if (position.getCost() != position.getTotalCost()) {
                body.append(DISCOUNT).append(String.format(FORMAT_NUMBER, position.getTotalCost()));
            }
            body.append("\n");
        }
        String past = super.print(cashReceipt);
        return past + body;
    }
}
