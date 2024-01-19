import static org.junit.Assert.*;

import model.Transaction;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

public class TransactionManagerTest {

    @Test
    public void testExactMatch() {
        // Create transactions for exact match
        Transaction buyerTransaction = new Transaction("1","1","18-Jan-23","123TEST", 18.0, 2099.00,0,188.91,188.91,2476.82);
        Transaction supplierTransaction = new Transaction("1","1","18-Jan-23","123TEST", 18.0, 2099.00,0,188.91,188.91,2476.82);;

        // Create buyer and seller documents
        Map<String, Transaction> buyerTransactions = new HashMap<>();
        buyerTransactions.put(buyerTransaction.getTransactionId(), buyerTransaction);

        Map<String, Transaction> supplierTransactions = new HashMap<>();
        supplierTransactions.put(supplierTransaction.getTransactionId(), supplierTransaction);

        // Reconcile transactions
        TransactionManager reconciler = new TransactionManager();
        Map<Transaction, String> reconciliationResult = null;
        try {
            reconciliationResult = reconciler.matchTransactions(buyerTransactions, supplierTransactions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Assert that the result contains an exact match
        Assert.assertEquals("Exact Match", reconciliationResult.get(buyerTransaction));
    }

    @Test
    public void testPartialMatch() {
        // Create transactions for partial match
        Transaction buyerTransaction = new Transaction("1","1","18-Jan-23","123TEST", 18.0, 2099.00,0,188.91,188.91,2476.82);
        Transaction supplierTransaction = new Transaction("1","1","20-Jan-23","123TEST", 18.0, 2099.00,0,188.91,188.91,2476.82);;

        // Create buyer and seller documents
        Map<String, Transaction> buyerTransactions = new HashMap<>();
        buyerTransactions.put(buyerTransaction.getTransactionId(), buyerTransaction);

        Map<String, Transaction> supplierTransactions = new HashMap<>();
        supplierTransactions.put(supplierTransaction.getTransactionId(), supplierTransaction);

        // Reconcile transactions
        TransactionManager reconciler = new TransactionManager();
        Map<Transaction, String> reconciliationResult = null;
        try {
            reconciliationResult = reconciler.matchTransactions(buyerTransactions, supplierTransactions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Assert that the result contains a partial match
        Assert.assertEquals("Partial Match", reconciliationResult.get(buyerTransaction));
    }

    @Test
    public void testOnlyInBuyer() {
        // Create a transaction only in the buyer document
        Transaction buyerTransaction = new Transaction("1","1","18-Jan-23","123TEST", 18.0, 2099.00,0,188.91,188.91,2476.82);

        // Create buyer and seller documents
        Map<String, Transaction> buyerTransactions = new HashMap<>();
        buyerTransactions.put(buyerTransaction.getTransactionId(), buyerTransaction);

        Map<String, Transaction> sellerTransactions = new HashMap<>();

        // Reconcile transactions
        TransactionManager reconciler = new TransactionManager();
        Map<Transaction, String> reconciliationResult = null;
        try {
            reconciliationResult = reconciler.matchTransactions(buyerTransactions, sellerTransactions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Assert that the result indicates only in buyer
        Assert.assertEquals("Only in Buyer", reconciliationResult.get(buyerTransaction));
    }

    @Test
    public void testOnlyInSupplier() {
        // Create a transaction only in the seller document
        Transaction supplierTransaction = new Transaction("1","1","18-Jan-23","123TEST", 18.0, 2099.00,0,188.91,188.91,2476.82);;

        // Create buyer and seller documents
        Map<String, Transaction> buyerTransactions = new HashMap<>();

        Map<String, Transaction> sellerTransactions = new HashMap<>();
        sellerTransactions.put(supplierTransaction.getTransactionId(), supplierTransaction);

        // Reconcile transactions
        TransactionManager reconciler = new TransactionManager();
        Map<Transaction, String> reconciliationResult = null;
        try {
            reconciliationResult = reconciler.matchTransactions(buyerTransactions, sellerTransactions);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Assert that the result indicates only in seller
        Assert.assertEquals("Only in Supplier", reconciliationResult.get(supplierTransaction));
    }
}
