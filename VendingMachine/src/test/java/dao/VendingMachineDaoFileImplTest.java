package dao;

import dto.InventoryItem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.VendingMachineServiceLayerImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineDaoFileImplTest {

    VendingMachineDao testDao;
    String testFile = "testInventory.txt";

    public VendingMachineDaoFileImplTest() throws VendingMachinePersistenceException{
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        //User interface: Spring automatically will cast from interface below based on xml file
        testDao = ctx.getBean("classRosterDao",VendingMachineDao.class);
    }


    @BeforeEach
    public void setup(){
        try {
            new PrintWriter(new FileWriter(testFile));
        } catch (IOException e) {
        }

        //testDao = new VendingMachineDaoFileImpl(testFile);
    }

    @Test
    void createItem() throws Exception{
        String itemName = "testItem1";
        InventoryItem testItem1 = new InventoryItem(itemName);
        BigDecimal testItem1Cost = new BigDecimal("0.99");
        testItem1.setItemCost(testItem1Cost);
        testItem1.setNumberInInventory(10);
        //testing of createItem
        testDao.createItem(itemName, testItem1);
        //includes testing of "readByName" method
        InventoryItem retrievedCreateItem = testDao.readByName(itemName);

        assertEquals(testItem1.getItemName(), retrievedCreateItem.getItemName(), "Checking item name.");
        assertEquals(testItem1.getItemCost(), retrievedCreateItem.getItemCost(), "Checking item cost.");
        assertEquals(testItem1.getNumberInInventory(), retrievedCreateItem.getNumberInInventory(), "Checking item inventory number.");
    }

    @Test
    void readAll() throws Exception{
        String itemName2 = "testItem2";
        InventoryItem testItem2 = new InventoryItem(itemName2);
        BigDecimal testItem2Cost = new BigDecimal("0.21");
        testItem2.setItemCost(testItem2Cost);
        testItem2.setNumberInInventory(2);
        testDao.createItem(itemName2, testItem2);

        String itemName3 = "testItem3";
        InventoryItem testItem3 = new InventoryItem(itemName3);
        BigDecimal testItem3Cost = new BigDecimal("7.49");
        testItem3.setItemCost(testItem3Cost);
        testItem3.setNumberInInventory(3);
        testDao.createItem(itemName3, testItem3);

        String itemName4 = "testItem4";
        InventoryItem testItem4 = new InventoryItem(itemName4);
        BigDecimal testItem4Cost = new BigDecimal("4.79");
        testItem4.setItemCost(testItem4Cost);
        testItem4.setNumberInInventory(4);
        testDao.createItem(itemName4, testItem4);

        List<InventoryItem> allInventoryItems = testDao.readAll();
        //below tests require Equals/Hashcode methods in object to work
        assertNotNull(allInventoryItems, "This list of items is should not be null.");
        assertEquals(3, allInventoryItems.size(), "List of items should have 3 items");
        assertTrue(testDao.readAll().contains(testItem2), "The list includes testItem2");
        assertTrue(testDao.readAll().contains(testItem3), "The list includes testItem3");
        assertTrue(testDao.readAll().contains(testItem4), "The list includes testItem4");
    }

    @Test
    void updateItemTest() throws Exception {
        String itemName5 = "testItem5";
        InventoryItem testItem5 = new InventoryItem(itemName5);
        BigDecimal testItem5Cost = new BigDecimal("5.55");
        testItem5.setItemCost(testItem5Cost);
        testItem5.setNumberInInventory(2);
        testDao.createItem(itemName5, testItem5);
        //original item5
        InventoryItem originalItem5 = testDao.readByName(testItem5.getItemName());
        assertEquals(2, originalItem5.getNumberInInventory());
        assertEquals(testItem5Cost, originalItem5.getItemCost());

        //update item with new values and use "updateItem" to save data
        InventoryItem updatedItem5 = new InventoryItem("UpdatedItem5");
        BigDecimal newItem5Cost = new BigDecimal("1.00");
        //cost and inventory changed via updateItem()
        updatedItem5.setItemCost(newItem5Cost);
        updatedItem5.setNumberInInventory(10);
        testDao.updateItem(updatedItem5);
        //test updated item values saved to file
        InventoryItem updatedItemTest5 = testDao.readByName("UpdatedItem5");
        assertEquals(10, updatedItem5.getNumberInInventory());
        assertEquals(newItem5Cost, updatedItem5.getItemCost());
    }


    @Test
    void deleteItem() throws Exception{
        String itemName7 = "testItem7";
        InventoryItem testItem7 = new InventoryItem(itemName7);
        BigDecimal testItem7Cost = new BigDecimal("1.99");
        testItem7.setItemCost(testItem7Cost);
        testItem7.setNumberInInventory(7);
        testDao.createItem(itemName7, testItem7);

        String itemName8 = "testItem8";
        InventoryItem testItem8 = new InventoryItem(itemName8);
        BigDecimal testItem8Cost = new BigDecimal("0.30");
        testItem8.setItemCost(testItem8Cost);
        testItem8.setNumberInInventory(8);
        testDao.createItem(itemName8, testItem8);

        InventoryItem removedItem7 = testDao.deleteItem(testItem7.getItemName());
        assertEquals(removedItem7, testItem7, "The removed item should be testItem7");

        List<InventoryItem> allItems = testDao.readAll();
        assertNotNull(allItems, "All items list should not be null");
        assertEquals(1, allItems.size(), "allItems should have 1 item in the List");

        InventoryItem removedItem8 = testDao.deleteItem(testItem8.getItemName());
        assertEquals(removedItem8, testItem8, "The removed item should be testItem8");

        allItems = testDao.readAll();
        assertTrue(allItems.isEmpty(), "The list of items should be empty now.");

        InventoryItem secondRemovedItem7 = testDao.readByName(testItem7.getItemName());
        assertNull(secondRemovedItem7, "testItem7 was removed, should be null.");
        InventoryItem secondRemovedItem8 = testDao.readByName(testItem8.getItemName());
        assertNull(secondRemovedItem8, "testItem8 was removed, should be null.");
    }
}