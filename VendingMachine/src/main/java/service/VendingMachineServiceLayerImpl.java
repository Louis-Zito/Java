package service;

import dao.VendingMachineAuditDao;
import dao.VendingMachineDao;
import dao.VendingMachinePersistenceException;
import dto.Change;
import dto.InventoryItem;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer {

    private VendingMachineDao VMDao;
    private VendingMachineAuditDao VMADao;

    public VendingMachineServiceLayerImpl(VendingMachineDao VMDao, VendingMachineAuditDao VMADao) {
        this.VMDao = VMDao;
        this.VMADao = VMADao;
    }

    @Override
    public void addItem(InventoryItem item) throws VendingMachinePersistenceException {
        VMDao.createItem(item.getItemName(), item);
        VMADao.writeAuditEntry("Item added to inventory: " + item.getItemName() + " ADDED.");
    }

    @Override
    public InventoryItem removedInventoryItem(String itemName) throws VendingMachinePersistenceException {
        InventoryItem removedItem = VMDao.deleteItem(itemName);
        //avoids error if null item attempts to be written to log
        if (removedItem != null){
            VMADao.writeAuditEntry("Item removed from inventory: " + removedItem.getItemName() + " DELETED.");
        }
        return removedItem;
    }

    //2 methods needed: getAvailableItems and purchaseItem
    @Override
    //STREAMS USED
    public List<InventoryItem> getAvailableItems() throws VendingMachinePersistenceException {
        //retrieve list with all items, including in stock = 0
        List<InventoryItem> zeroItemNotRemoved = VMDao.readAll();
        //use Streams/Lambda to return List with inventory = zero excluded
        List<InventoryItem> outOfStockRemoved = zeroItemNotRemoved.stream()
                .filter((item) -> item.getNumberInInventory() > 0)
                .collect(Collectors.toList());
        return outOfStockRemoved;
    }

    //split check inventory from check prices as if combined entering prices for no reason if inventory = 0
    public InventoryItem checkInventory(String itemName) throws VendingMachinePersistenceException, NoItemInventoryException {
        InventoryItem item = VMDao.readByName(itemName);
        if (item.getNumberInInventory() < 1){
            throw new NoItemInventoryException("Error: that item is out of inventory.");
        }
        return item;
    }

    public Change purchaseItem(InventoryItem itemToBuy, BigDecimal amountPaid)
            throws VendingMachinePersistenceException, InsufficientFundsException{
        BigDecimal costOfItem = itemToBuy.getItemCost();
        //costOfItem > amountPaid == 1
        if (costOfItem.compareTo(amountPaid) > 0) {
            throw new InsufficientFundsException("ERROR: Payment amount does not cover cost.");
        }
        //pass both cost and inventory number checks, reduce by 1
        itemToBuy.setNumberInInventory((itemToBuy.getNumberInInventory()) - 1);
        VMDao.updateItem(itemToBuy);
        BigDecimal changeDue = amountPaid.subtract(costOfItem);
        Change coinDenominations = new Change(changeDue);
        return coinDenominations;
    }
}//end class


