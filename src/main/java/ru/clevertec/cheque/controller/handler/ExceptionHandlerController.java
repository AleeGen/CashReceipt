package ru.clevertec.cheque.controller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.clevertec.cheque.exception.CashReceiptException;
import ru.clevertec.cheque.exception.HumanException;
import ru.clevertec.cheque.exception.responce.CashReceiptErrorResponse;
import ru.clevertec.cheque.exception.responce.HumanErrorResponse;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler
    public ResponseEntity<CashReceiptErrorResponse> handleException(CashReceiptException e) {
        CashReceiptErrorResponse error = new CashReceiptErrorResponse();
        error.setMessage(e.getMessage());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setTimeStamp(System.currentTimeMillis());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(Exception e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
