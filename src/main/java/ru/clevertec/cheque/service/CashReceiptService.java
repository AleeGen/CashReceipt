package ru.clevertec.cheque.service;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.cheque.dao.CardRepository;
import ru.clevertec.cheque.dao.ProductRepository;
import ru.clevertec.cheque.service.util.CashReceiptPdf;
import ru.clevertec.cheque.entity.*;
import ru.clevertec.cheque.exception.CashReceiptException;
import ru.clevertec.cheque.service.util.calculator.impl.DiscountCardCalculator;
import ru.clevertec.cheque.service.util.calculator.impl.PromotionalProductsCalculator;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;

@Service
public class CashReceiptService {
    private final String BACKGROUND = "src/main/resources/Clevertec_Template.pdf";
    private final Organization organization;
    private final ProductRepository productRep;
    private final CardRepository cardRep;

    public CashReceiptService(ProductRepository productRep, CardRepository cardRep) {
        this.productRep = productRep;
        this.cardRep = cardRep;
        organization = Organization.builder()
                .name("Clevertec")
                .address("90 Dubninskaya Street, Moscow, Russia")
                .email("info@clevertec.ru")
                .telephone("+7 (499) 653 94 51").build();
    }

    @Transactional
    public CashReceipt getCashReceipt(Integer[] productId, Integer numberCard) {
        List<Position> positions = getPositions(productId);
        Optional<DiscountCard> card = getCard(numberCard);
        CashReceipt cashReceipt = new CashReceipt.CashReceiptBuilder(positions)
                .addDiscountCard(card)
                .addOrganization(organization)
                .addDate(new Date()).build();
        new PromotionalProductsCalculator().calculate(cashReceipt);
        new DiscountCardCalculator().calculate(cashReceipt);
        return cashReceipt;
    }

    public byte[] getPdfCashReceipt(CashReceipt cashReceipt) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             PdfDocument pdfDocument = new PdfDocument(new PdfReader(BACKGROUND), new PdfWriter(baos));
             Document document = new Document(pdfDocument, PageSize.A4.rotate())) {
            CashReceiptPdf cashReceiptPdf = new CashReceiptPdf();
            Div div = new Div()
                    .setFont(PdfFontFactory.createFont(StandardFonts.COURIER))
                    .setTextAlignment(TextAlignment.CENTER)
                    .setRelativePosition(0, 180, 0, 50)
                    .setBorderBottom(new SolidBorder(Border.SOLID))
                    .add(cashReceiptPdf.addHeader(cashReceipt))
                    .add(cashReceiptPdf.addBody(cashReceipt))
                    .add(cashReceiptPdf.addFooter(cashReceipt));
            document.add(div);
            document.close();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new CashReceiptException("Error when creating pdf");
        }
    }

    private List<Position> getPositions(Integer[] productId) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer id : productId) {
            var plug = map.containsKey(id) ? map.put(id, map.get(id) + 1) : map.put(id, 1);
        }
        List<Position> positions = new ArrayList<>();
        for (Integer id : map.keySet()) {
            Product product = productRep.findById(id).orElseThrow(() ->
                    new CashReceiptException(String.format("Product with id = %d not found", id)));
            positions.add(new Position(map.get(id), product));
        }
        return positions;
    }

    private Optional<DiscountCard> getCard(Integer numberCard) {
        if (numberCard == null) {
            return Optional.empty();
        } else {
            DiscountCard c = cardRep.findById(numberCard).orElseThrow(() ->
                    new CashReceiptException(String.format("Discount card with number = %d not found", numberCard)));
            return Optional.of(c);
        }
    }

}