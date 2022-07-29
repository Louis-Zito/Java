package service;

import dao.*;
import dto.Change;
import dto.InventoryItem;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class VendingMachineServiceLayerImplTest {
    private VendingMachineServiceLayer service;
    private VendingMachineDao testDao;
    private VendingMachineAuditDao testAuditDao;

    public VendingMachineServiceLayerImplTest() throws VendingMachinePersistenceException {
//        testDao = new VendingMachineDaoStubImpl();
//        testAuditDao = new VendingMachineAuditDaoStubImpl();
//        service = new VendingMachineServiceLayerImpl(testDao,testAuditDao);
          ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
          //User interface: Spring automatically will cast from interface below based on xml file
          service = ctx.getBean("serviceLayer", VendingMachineServiceLayer.class);
    }

    @Test
    void addItem() {
        InventoryItem item = new InventoryItem(("testItem"));
        BigDecimal itemCost = new BigDecimal("1.49");
        item.setItemCost(itemCost);
        item.setNumberInInventory(1);
        try{
            service.addItem(item);
        } catch
            (VendingMachinePersistenceException e){
                fail("Inventory item valid, no exception should have been thrown.");
            }
        }

    @Test
    void removedInventoryItem() throws VendingMachinePersistenceException {
        InventoryItem item = new InventoryItem(("testItem"));
        BigDecimal itemCost = new BigDecimal("1.49");
        item.setItemCost(itemCost);
        item.setNumberInInventory(1);

        InventoryItem shouldBe_testItem = service.removedInventoryItem(item.getItemName());
        assertNotNull(shouldBe_testItem, "Should return item and not be null");
        assertEquals(shouldBe_testItem, item, "Should be the same as testItem");

        InventoryItem shouldBeNull = service.removedInventoryItem("noItemExists");
        assertNull(shouldBeNull);
    }

    @Test
    void getAvailableItems() throws VendingMachinePersistenceException {
        InventoryItem item = new InventoryItem(("testItem"));
        BigDecimal itemCost = new BigDecimal("1.49");
        item.setItemCost(itemCost);
        item.setNumberInInventory(1);

        assertEquals(1, service.getAvailableItems().size(),"Should have only 1 item.");
        assertTrue(service.getAvailableItems().contains(item),"The one item should be  testItem");
    }

    @Test
    void checkInventory() throws VendingMachinePersistenceException, NoItemInventoryException {

        InventoryItem testItem = new InventoryItem(("testItem"));
        BigDecimal itemCost = new BigDecimal("1.49");
        testItem.setItemCost(itemCost);
        testItem.setNumberInInventory(1);

        int testItemInventory = testItem.getNumberInInventory();
        assertEquals(1, testItemInventory, "testItem inventory should equal 1");

        InventoryItem outOfInventoryItem = new InventoryItem(("itemOutOfStock"));
        BigDecimal outOfInventoryCost = new BigDecimal("1.49");
        outOfInventoryItem.setItemCost(outOfInventoryCost);
        outOfInventoryItem.setNumberInInventory(0);

        try{
            //Inventory of outOfInventoryItem inventory no. = 0, should throw NoItemInventoryException
            InventoryItem zeroInventoryItem = service.checkInventory(outOfInventoryItem.getItemName());
            fail("Expected NoItemInventoryException not thrown");
        } catch (NoItemInventoryException e){
            return;
        }
    }

    @Test
    void purchaseItem() throws InsufficientFundsException, VendingMachinePersistenceException {
        InventoryItem testItem = new InventoryItem(("testItem"));
        BigDecimal itemCost = new BigDecimal("1.49");
        testItem.setItemCost(itemCost);
        testItem.setNumberInInventory(1);

        try{
            //Cost 0.89 > paid 0.50, should throw InsufficientFundsException
            BigDecimal fiftyCents = new BigDecimal(0.50);
            Change change = service.purchaseItem(testItem, fiftyCents);
            fail("Expected InsufficientFundsException not thrown");
        } catch (InsufficientFundsException e){
            return;
        }

        try{
            //2.96 - 1.49 = 1.47 due: 1 dollar, 1 quarter, 2 dimes, 2 pennies
            BigDecimal paidAmount = new BigDecimal(2.96);
            Change change = service.purchaseItem(testItem, paidAmount);
            assertNotNull(change, "Should return a change object");
            //expected total change: 1.47
            BigDecimal changeDifference = new BigDecimal("1.47");
            BigDecimal dollarsExpected1 = new BigDecimal("1");
            BigDecimal dimesExpected1 = new BigDecimal("2");
            BigDecimal penniesExpected1 = new BigDecimal("2");
            Change correctChange = new Change(changeDifference);
            assertEquals(correctChange.getDollars(), dollarsExpected1, "Should contain 1 dollar in change");
            assertEquals(correctChange.getDimes(), dimesExpected1, "Should contain 2 dimes in change");
            assertEquals(correctChange.getPennies(), penniesExpected1, "Should contain 2 pennies in change");
        } catch (InsufficientFundsException e){
            return;
        }
    }//end purchaseItemTests
}//end class
