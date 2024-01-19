import model.Transaction;
import utility.TransactionUtility;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        String file1="buyer.csv";
        String file2="supplier.csv";
        String file3="TransactionMatch.csv";
        Map<Transaction, String> matchTransactionMap = new HashMap<>();
        try {
            Map<String, Transaction> buyerTransactionMap = TransactionUtility.CSVParse(file1);
            Map<String,Transaction> supplierTransactionMap = TransactionUtility.CSVParse(file2);
            TransactionManager transactionManager = new TransactionManager();
            matchTransactionMap = transactionManager.matchTransactions(buyerTransactionMap,supplierTransactionMap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String header = TransactionUtility.headerList.concat(",").concat("Category,").concat(TransactionUtility.headerList);
        try {
            FileWriter writer = new FileWriter(file3);
            writer.append(header);
            writer.append('\n');
            for(Map.Entry<Transaction,String> entry : matchTransactionMap.entrySet()) {
                String transactionString = Transaction.getTransactionString(entry.getKey());
                transactionString = transactionString.concat(" ,").concat(entry.getValue());
                writer.append(transactionString);
                writer.append('\n');
            }
            writer.flush();
            writer.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}

/*


+----------------------------------+          +---------------------------+
|      Transaction                 |          |   TransactionManager      |
+----------------------------------+          +---------------------------+
| - transactionId: String          |          | - matchTransactions()     |
| - gstIn: String                  |          | - isPartialTransaction()  |
| - transactionDate: String        |          +---------------------------+
| - billNumber: String             |
| - gstRate: double                |
| - taxableValue: double           |          +---------------------------+
| - igst: double                   |          |    TransactionUtility     |
| - cgst: double                   |          +---------------------------+
| - sgst: double                   |          | - CSVParse()              |
| - total: double                  |          | - getLabelMap()           |
+----------------------------------+          +---------------------------+


*/
