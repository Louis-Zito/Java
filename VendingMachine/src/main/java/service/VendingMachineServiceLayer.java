package service;

import dao.VendingMachinePersistenceException;
import dto.Change;
import dto.InventoryItem;

import java.math.BigDecimal;
import java.util.List;

public interface VendingMachineServiceLayer {

    InventoryItem checkInventory(String itemName) throws VendingMachinePersistenceException, NoItemInventoryException;

    void addItem(InventoryItem item) throws VendingMachinePersistenceException;

    InventoryItem removedInventoryItem(String itemName) throws VendingMachinePersistenceException;

    List<InventoryItem> getAvailableItems() throws
            VendingMachinePersistenceException;

    Change purchaseItem(InventoryItem item, BigDecimal amountPaid) throws
            VendingMachinePersistenceException,
            InsufficientFundsException;
}

