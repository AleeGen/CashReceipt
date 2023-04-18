package ru.clevertec.cheque.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.cheque.exception.CardException;
import ru.clevertec.cheque.exception.CashReceiptException;
import ru.clevertec.cheque.exception.ProductException;
import ru.clevertec.cheque.exception.responce.CardErrorResponse;
import ru.clevertec.cheque.exception.responce.CashReceiptErrorResponse;
import ru.clevertec.cheque.exception.responce.AbstractErrorResponse;
import ru.clevertec.cheque.exception.responce.ProductErrorResponse;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler
    public ResponseEntity<CashReceiptErrorResponse> handleException(CashReceiptException e) {
        return getResponse(new CashReceiptErrorResponse(), e);
    }

    @ExceptionHandler
    public ResponseEntity<CardErrorResponse> handleException(CardException e) {
        return getResponse(new CardErrorResponse(), e);
    }

    @ExceptionHandler
    public ResponseEntity<ProductErrorResponse> handleException(ProductException e) {
        return getResponse(new ProductErrorResponse(), e);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private <T extends AbstractErrorResponse> ResponseEntity<T> getResponse(T error, Exception e) {
        error.setMessage(e.getMessage());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

}