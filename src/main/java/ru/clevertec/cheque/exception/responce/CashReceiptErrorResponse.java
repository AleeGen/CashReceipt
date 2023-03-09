package ru.clevertec.cheque.exception.responce;

import lombok.Data;

@Data
public class CashReceiptErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
