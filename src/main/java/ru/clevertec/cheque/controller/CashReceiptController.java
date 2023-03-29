package ru.clevertec.cheque.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/cheque")
public class CashReceiptController {

    @Autowired
    private CashReceiptService cashReceiptService;

    @GetMapping("/get")
    public String getCheque(@RequestParam Integer[] itemId, @RequestParam(required = false) Integer card) {
        CashReceipt cashReceipt = cashReceiptService.getCashReceipt(itemId, card);
        return new FooterDecorator(new BodyDecorator(new HeaderDecorator(new CashReceiptPrinter()))).print(cashReceipt);
    }

}
