package ru.clevertec.cheque.util.calculation;

import ru.clevertec.cheque.entity.CashReceipt;

public interface CashReceiptCalculator {
    CashReceipt calculate(CashReceipt cashReceipt);
}
