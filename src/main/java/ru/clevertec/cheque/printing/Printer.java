package ru.clevertec.cheque.printing;

public interface Printer<T> {
    String print(T t);
}
