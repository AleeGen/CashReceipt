package printing.decorator;

import entity.CashReceipt;
import printing.Printer;

public abstract class PrinterDecorator implements Printer<CashReceipt> {

    protected static final String SEPARATOR =
            "\n--------------------------------------------------------------------------------\n";
    protected static final String TAB = "\t\t\t\t\t\t";
    protected static final String FORMAT_NUMBER = "%.2f";

    private Printer<CashReceipt> printer;

    protected PrinterDecorator(Printer<CashReceipt> printer) {
        this.printer = printer;
    }

    @Override
    public String print(CashReceipt cashReceipt) {
        return printer.print(cashReceipt);
    }
}
