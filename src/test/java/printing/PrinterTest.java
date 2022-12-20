package printing;

import entity.CashReceipt;
import helper.HelperTest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import printing.decorator.BodyDecorator;
import printing.decorator.FooterDecorator;
import printing.decorator.HeaderDecorator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PrinterTest {
    private CashReceiptPrinter printer = new CashReceiptPrinter();

    private String headerExpected;
    private String bodyExpected;
    private String footerExpected;

    @BeforeMethod
    public void before(){
        StringBuilder str = new StringBuilder();
        try (BufferedReader readerHeader = new BufferedReader(new FileReader(ClassLoader.getSystemResource("header_decorator.txt").getPath()));
             BufferedReader readerBody = new BufferedReader(new FileReader(ClassLoader.getSystemResource("body_decorator.txt").getPath()));
             BufferedReader readerFooter = new BufferedReader(new FileReader(ClassLoader.getSystemResource("footer_decorator.txt").getPath()))) {
            while (readerHeader.ready()) {
                str.append(readerHeader.readLine()).append("\n");
            }
            headerExpected = str.substring(0, str.length() - 1);
            str = new StringBuilder();
            while (readerBody.ready()) {
                str.append(readerBody.readLine()).append("\n");
            }
            bodyExpected = str.toString();
            str = new StringBuilder();
            while (readerFooter.ready()) {
                str.append(readerFooter.readLine()).append("\n");
            }
            footerExpected = str.substring(0, str.length() - 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testHeader() {
        CashReceipt cashReceipt = HelperTest.getCashReceipt();
        String actual = new HeaderDecorator(printer).print(cashReceipt);
        Assert.assertEquals(actual, headerExpected);
    }

    @Test
    public void testBody() {
        CashReceipt cashReceipt = HelperTest.getCashReceipt();
        String actual = new BodyDecorator(printer).print(cashReceipt);
        Assert.assertEquals(actual, bodyExpected);
    }

    @Test
    public void testFooter() {
        CashReceipt cashReceipt = HelperTest.getCashReceipt();
        String actual = new FooterDecorator(printer).print(cashReceipt);
        Assert.assertEquals(actual, footerExpected);
    }
}