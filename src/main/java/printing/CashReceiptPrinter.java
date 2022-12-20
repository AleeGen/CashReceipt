package printing;

import entity.CashReceipt;

public class CashReceiptPrinter implements Printer<CashReceipt> {

    private static final String NAME = "CASH RECEIPT";
    private static final String SEPARATOR =
            "\n--------------------------------------------------------------------------------\n";
    private static final String TAB = "\t\t\t\t\t\t\t\t";

    @Override
    public String print(CashReceipt cashReceipt) {
        return new StringBuilder()
                .append(SEPARATOR)
                .append(TAB)
                .append(NAME)
                .toString();
    }
}
