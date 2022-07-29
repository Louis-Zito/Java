package service;

import dao.VendingMachineDao;
import dao.VendingMachinePersistenceException;
import dto.InventoryItem;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class VendingMachineDaoStubImpl implements VendingMachineDao {

    public InventoryItem onlyItem;
    public InventoryItem outOfInventory;

    public VendingMachineDaoStubImpl(){
        onlyItem = new InventoryItem("testItem");
        BigDecimal onlyItemCost = new BigDecimal("1.49");
        onlyItem.setItemCost(onlyItemCost);
        onlyItem.setNumberInInventory(1);

        outOfInventory = new InventoryItem("itemOutOfStock");
        BigDecimal outOfInventory_Cost = new BigDecimal("1.49");
        outOfInventory.setItemCost(outOfInventory_Cost);
        outOfInventory.setNumberInInventory(0);
    }

    public VendingMachineDaoStubImpl(InventoryItem testItem){
        this.onlyItem = testItem;
    }


    @Override
    public InventoryItem createItem(String itemName, InventoryItem item) throws VendingMachinePersistenceException {
        if (itemName.equals(item.getItemName())){
            return onlyItem;
        } else{
            return null;
        }
    }

    @Override
    public List<InventoryItem> readAll() throws VendingMachinePersistenceException {
        List<InventoryItem> itemList = new ArrayList<>();
        itemList.add(onlyItem);
        return itemList;
    }

    @Override
    public InventoryItem readByName(String itemName) throws VendingMachinePersistenceException {
        InventoryItem returnedItem = new InventoryItem(itemName);
        return returnedItem;
    }

    @Override
    public InventoryItem updateItem(InventoryItem item) throws VendingMachinePersistenceException {
        String itemName = item.getItemName();
        if (itemName.equals(onlyItem.getItemName())){
            return onlyItem;
        } else {
            return null;
        }
    }

    @Override
    public InventoryItem deleteItem(String itemName) throws VendingMachinePersistenceException {
        if (itemName.equals(onlyItem.getItemName())){
            return onlyItem;
        } else{
            return null;
        }
    }
}

