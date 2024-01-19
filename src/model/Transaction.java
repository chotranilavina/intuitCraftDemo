package model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {

    private String transactionId;
    private String gstIn;
    private String transactionDate;
    private String billNumber;
    private double gstRate;
    private double taxableValue;
    private double igst;
    private double cgst;
    private double sgst;
    private double total;

    public static String getTransactionString (Transaction a) {
        List<String> values = Arrays.asList(a.gstIn,
                a.getTransactionDate(),
                a.getBillNumber(),
                String.valueOf(a.getGstRate()),
                String.valueOf(a.getTaxableValue()),
                String.valueOf(a.getIgst()),
                String.valueOf(a.getCgst()),
                String.valueOf(a.getSgst()),
                String.valueOf(a.getTotal()));
        return String.join(" ,",values);
    }

}