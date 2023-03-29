package ru.clevertec.cheque.exception.responce;

import lombok.Data;

@Data
public class HumanErrorResponse {
    private int status;
    private String message;
    private long timeStamp;
}
