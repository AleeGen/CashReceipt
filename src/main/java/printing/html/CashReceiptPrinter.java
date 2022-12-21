package printing.html;

import entity.CashReceipt;
import printing.Printer;

public class CashReceiptPrinter implements Printer<CashReceipt> {

    private static final String NAME = "CASH RECEIPT";
    private static final String SEPARATOR =
            "<br>--------------------------------------------------------------------------------<br>";
    private static final String TAB = "&nbsp;&nbsp;&nbsp;&nbsp;";

    @Override
    public String print(CashReceipt cashReceipt) {
        return new StringBuilder()
                .append(SEPARATOR)
                .append(TAB)
                .append(NAME)
                .toString();
    }
}
