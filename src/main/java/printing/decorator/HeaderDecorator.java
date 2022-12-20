package printing.decorator;

import entity.CashReceipt;
import entity.Organization;
import printing.Printer;

public class HeaderDecorator extends PrinterDecorator {
    private static final String DATE = "DATE: ";

    public HeaderDecorator(Printer<CashReceipt> printer) {
        super(printer);
    }

    @Override
    public String print(CashReceipt cashReceipt) {
        Organization organization = cashReceipt.getOrganization();
        StringBuilder header = new StringBuilder()
                .append(SEPARATOR).append(TAB)
                .append(organization.getName())
                .append("\n").append(TAB)
                .append(organization.getAddress())
                .append("\n").append(TAB)
                .append(organization.getTelephone())
                .append("\n").append(TAB)
                .append(organization.getEmail())
                .append(SEPARATOR).append(TAB)
                .append(DATE).append(cashReceipt.getDate());
        String past = super.print(cashReceipt);
        return past + header;
    }
}
