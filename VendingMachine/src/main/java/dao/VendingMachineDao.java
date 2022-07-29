package dao;

import dto.InventoryItem;

import java.math.BigDecimal;
import java.util.List;

public interface VendingMachineDao {

    //keep DAO strictly to naming/behavior of CRUD: Create/Read -can be multiple/Update/Delete; CUD single methods;
    InventoryItem createItem(String itemName, InventoryItem item) throws VendingMachinePersistenceException;

    List<InventoryItem> readAll() throws VendingMachinePersistenceException;

    InventoryItem readByName(String itemName) throws VendingMachinePersistenceException;

    InventoryItem updateItem(InventoryItem item) throws VendingMachinePersistenceException;

    InventoryItem deleteItem(String itemName) throws VendingMachinePersistenceException;

}
