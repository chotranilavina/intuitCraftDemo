package utility;

import model.Transaction;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

public class TransactionUtility {
    public static String headerList = null;
    public static Map<String,Transaction> CSVParse (String fileName) throws IOException {
        Map<String,Transaction> transactionMap = new HashMap<>();
        Map<String,String> labelMap = getLabelMap();
        BufferedReader CSVFile1 = new BufferedReader(new FileReader(fileName));
        List<String> headers = new ArrayList<>();
        String dataRow1 = CSVFile1.readLine();
        if (dataRow1 != null)
        {
            headerList = dataRow1;
            String[] dataArray1 = dataRow1.split(",");
            for (String headerCol:dataArray1)
            {
                headers.add(headerCol);
            }
            dataRow1 = CSVFile1.readLine();
        }
        int lineNumber=0;
        while (dataRow1 != null)
        {
            String[] dataArray1 = dataRow1.split(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*[^\\\"]*$)");
            Transaction transaction = new Transaction();
            for (int i=0;i<dataArray1.length;i++)
            {
                if(labelMap.containsKey(headers.get(i))) {
                    String fieldName = labelMap.get(headers.get(i));
                    try {
                        Field field = transaction.getClass().getDeclaredField(fieldName);
                        field.setAccessible(true);
                        if(dataArray1[i].length()==0) {
                            try {
                                field.set(transaction, 0);
                            } catch (Exception e) {
//                                System.out.println(e.getMessage());
                                field.set(transaction,null);
                            }
                        } else {
                            try {
                                field.setDouble(transaction, Double.parseDouble(dataArray1[i].replace("\"", "").replace(",", "")));
                            } catch (Exception e) {
//                                System.out.println(e.getMessage());
                                field.set(transaction, String.valueOf(dataArray1[i]).replace("\"", ""));
                            }
                        }
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            String transactionId = transaction.getGstIn() + transaction.getBillNumber();
            transaction.setTransactionId(transactionId);
            transactionMap.put(transactionId,transaction);
            dataRow1 = CSVFile1.readLine(); // Read next line of data.
            lineNumber++;
        }
        CSVFile1.close();
        return transactionMap;
    }

    public static Map<String,String> getLabelMap() {
        Map<String,String> labelMap = new HashMap<>();
        labelMap.put("GSTIN","gstIn");
        labelMap.put("Date","transactionDate");
        labelMap.put("Bill no","billNumber");
        labelMap.put("GST rate(%)","gstRate");
        labelMap.put("Taxable value","taxableValue");
        labelMap.put("IGST","igst");
        labelMap.put("CGST","cgst");
        labelMap.put("SGST","sgst");
        labelMap.put("Total","total");
        return labelMap;
    }
}
