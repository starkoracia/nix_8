package ua.com.alevel.service.util;

import ua.com.alevel.entities.Transaction;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class TransactionsCsvUtil {

    private TransactionsCsvUtil() {
    }

    public static String getCsvStringFromTransactions(List<Transaction> transactions) {
        StringBuffer sb = new StringBuffer();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        sb.append("Сумма").append(",")
                .append("Категория").append(",")
                .append("Баланс после").append(",")
                .append("Время перевода").append("\n");
        transactions.forEach((tr) -> {
            sb.append(tr.getAmount()).append(",")
                    .append(tr.getCategory().getName()).append(",")
                    .append(tr.getAccountBalanceAfter()).append(",")
                    .append(formatter.format(LocalDateTime.ofInstant(tr.getDateTime().toInstant(), tr.getDateTime().getTimeZone().toZoneId()))).append("\n");
        });
        System.out.println("sb = " + sb);
        return sb.toString();
    }

}
