package ru.clevertec.cheque.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.cheque.entity.CashReceipt;
import ru.clevertec.cheque.service.util.printing.decorator.BodyDecorator;
import ru.clevertec.cheque.service.util.printing.decorator.CashReceiptPrinter;
import ru.clevertec.cheque.service.util.printing.decorator.FooterDecorator;
import ru.clevertec.cheque.service.util.printing.decorator.HeaderDecorator;
import ru.clevertec.cheque.service.CashReceiptService;

import java.io.IOException;

@RestController
@RequestMapping("/cashReceipt")
public class CashReceiptController {

    private final CashReceiptService cashReceiptService;

    public CashReceiptController(CashReceiptService cashReceiptService) {
        this.cashReceiptService = cashReceiptService;
    }

    @GetMapping("/txt")
    public String getCheque(@RequestParam Integer[] id, @RequestParam(required = false) Integer card) {
        CashReceipt cashReceipt = cashReceiptService.getCashReceipt(id, card);
        return new FooterDecorator(new BodyDecorator(new HeaderDecorator(new CashReceiptPrinter()))).print(cashReceipt);
    }

    @GetMapping("/pdf")
    public void getChequePdf(@RequestParam Integer[] id,
                                 @RequestParam(required = false) Integer card,
                                 HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");
        CashReceipt cashReceipt = cashReceiptService.getCashReceipt(id, card);
        byte[] pdf = cashReceiptService.getPdfCashReceipt(cashReceipt);
        response.getOutputStream().write(pdf);
    }

}