import model.Transaction;
import utility.TransactionUtility;

import java.io.*;
import java.util.*;

public class TransactionManager {
    public Map<Transaction, String> matchTransactions(Map<String,Transaction> buyerTransactionMap,
                                                      Map<String,Transaction> supplierTransactionMap) throws FileNotFoundException, IOException
    {

        Map<Transaction,String> matchTransactionMap = new HashMap<>();

        for(Map.Entry<String,Transaction> entry : buyerTransactionMap.entrySet())  {
            if(supplierTransactionMap.containsKey(entry.getKey()) &&
                    supplierTransactionMap.get(entry.getKey()).equals(entry.getValue())) {
                matchTransactionMap.put(entry.getValue(),"Exact Match");
            } else if (!supplierTransactionMap.containsKey(entry.getKey())) {
                matchTransactionMap.put(entry.getValue(),"Only in Buyer");
            } else {
                matchTransactionMap.put(entry.getValue(),"Partial Match");
            }
        }
        for(Map.Entry<String,Transaction> entry : supplierTransactionMap.entrySet())  {
            if(!buyerTransactionMap.containsKey(entry.getKey())) {
                matchTransactionMap.put(entry.getValue(),"Only in Supplier");
            }
        }
        return matchTransactionMap;
    }

    public static boolean isPartialTransaction(Transaction a, Transaction b, Long threshold) {
        return true;
    }
}
