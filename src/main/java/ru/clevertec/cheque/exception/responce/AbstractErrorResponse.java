package ru.clevertec.cheque.exception.responce;

import lombok.Data;

@Data
public abstract class AbstractErrorResponse {

    private String message;
    private long timeStamp;

}