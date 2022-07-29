package service;

import dao.*;
import dto.Order;
import dto.Product;
import dto.Tax;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

public class FlooringMasteryServiceLayerImplTest {
    private FlooringMasteryServiceLayer service;

    public FlooringMasteryServiceLayerImplTest() {
//        testOrderDao = new FlooringMasteryOrderDaoStubImpl();
//        productDao = new ProductDaoFileImpl();
//        taxDao = new TaxDaoFileImpl();
//        auditDao = new FlooringMasteryAudiDaoFileImpl();
//        service = new FlooringMasterServiceLayerImpl(testOrderDao, productDao, taxDao, auditDao );
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", FlooringMasteryServiceLayer.class);
        }

//    @BeforeEach
//    public void setup(){
//        String folder = "TestFolder\\";
//        String fileName = "05/25/2022";
//        testOrderDao = new OrderDaoFileImpl(fileName, folder);
//    }

    @Test
    //includes testing for gathering correct State & Product details
    //includes testing for calculations of all order fields
    void addOrder() throws TaxDataPersistenceException, ProductNotCurrentlySoldException, ProductDataPersistenceException,
                            IOException, FlooringMasteryPersistenceException, StateSalesNotAllowedException {
        String newDate = "05252022";
        Order newOrder = new Order(2);
        newOrder.setCustomerName("A");
        newOrder.setStateAbbreviation("TX");
        BigDecimal bdArea = new BigDecimal("100.00");
        newOrder.setArea(bdArea);
        newOrder.setProductType("Tile");
        Tax newTax = service.getTaxIfValidStateElseNull(newOrder.getStateAbbreviation());
        Product newProduct = service.getProductIfValidElseNull(newOrder.getProductType());
        //tested values that should result from orderCalculations()
        BigDecimal testArea = new BigDecimal("100.00");
        BigDecimal testTaxRate = new BigDecimal("4.45");
        BigDecimal testCpSF = new BigDecimal("3.50");
        BigDecimal testLCpSF = new BigDecimal("4.15");
        BigDecimal testMC = new BigDecimal("350.00");
        BigDecimal testLC = new BigDecimal("415.00");
        BigDecimal testTax = new BigDecimal("34.04");
        BigDecimal testTotal = new BigDecimal("799.04");

        Order addedOrder = null;
        try {
            addedOrder = service.addOrder(newOrder, newTax, newProduct, newDate);
        } catch
        (OrderDataPersistenceException e) {
            fail("Order valid, no exception should be thrown.");
        }

        //confirms the following methods from Service as all used in addOrder:
        //addOrder, getOrderNumber, orderCalculations,
        //addBigDecimal, multiplyBigDecimal, divideBigDecimal
        //getTaxIfValidElseNull, getProductIfValidElseNull
        assertEquals(2, addedOrder.getOrderNumber(), "Order number should be 1.");
        assertEquals("A", addedOrder.getCustomerName(), "Customer name should be A.");
        assertEquals("TX", addedOrder.getStateAbbreviation(), "State abbreviation should be TX");
        assertEquals("TILE", addedOrder.getProductType(), "Product type should be TILE.");
        assertEquals(testArea, addedOrder.getArea(), "Area should be 100.");
        assertEquals(testTaxRate, addedOrder.getTaxRate(), "Tax rate should be 4.45");
        assertEquals(testCpSF, addedOrder.getCostPerSquareFoot(), "CpSF should be 3.50.");
        assertEquals(testLCpSF, addedOrder.getLaborCostPerSquareFoot(), "LCpSF should be 4.15.");
        assertEquals(testMC, addedOrder.getMaterialCost(), "MC should be 350.00");
        assertEquals(testLC, addedOrder.getLaborCost(), "LC should be 415.");
        assertEquals(testTax, addedOrder.getTax(), "Tax should be 34.04.");
        assertEquals(testTotal, addedOrder.getTotal(), "Invoice total should be 799.04.");
    }

    @Test
    void removeOrder() throws TaxDataPersistenceException, ProductNotCurrentlySoldException, ProductDataPersistenceException, OrderDataPersistenceException, FlooringMasteryPersistenceException {
        String newDate = "05252022";
        Order newOrder = new Order(2);
        newOrder.setCustomerName("A");
        newOrder.setStateAbbreviation("TX");
        BigDecimal bdArea = new BigDecimal("100.00");
        newOrder.setArea(bdArea);
        newOrder.setProductType("Tile");
        Tax newTax = service.getTaxIfValidStateElseNull(newOrder.getStateAbbreviation());
        Product newProduct = service.getProductIfValidElseNull(newOrder.getProductType());

        Order addedOrder = null;
        try {
            addedOrder = service.addOrder(newOrder, newTax, newProduct, newDate);
        } catch
        (OrderDataPersistenceException | StateSalesNotAllowedException | IOException | FlooringMasteryPersistenceException e) {
            fail("Order valid, no exception should be thrown.");
        }

        Order removedOrder = service.removeOrder(addedOrder, newDate);
        assertNotNull(removedOrder, "removeOrder return value should not be null.");
        assertEquals(removedOrder.getOrderNumber(), addedOrder.getOrderNumber(), "Should return order with the same order number.");
    }

    @Test
    void editOrder() throws TaxDataPersistenceException, ProductNotCurrentlySoldException,
            ProductDataPersistenceException, FlooringMasteryPersistenceException {
        String newDate = "05252022";
        Order newOrder = new Order(2);
        newOrder.setCustomerName("B");
        newOrder.setStateAbbreviation("UT");
        BigDecimal bdArea = new BigDecimal("200.00");
        newOrder.setArea(bdArea);
        newOrder.setProductType("Carpet");
        Tax newTax = service.getTaxIfValidStateElseNull(newOrder.getStateAbbreviation());
        Product newProduct = service.getProductIfValidElseNull(newOrder.getProductType());
        //tested values that should result from orderCalculations() after updatedOrder
        BigDecimal testArea = new BigDecimal("200.00");
        Order updatedOrder = null;

        try {
            updatedOrder = service.editOrder(newOrder, newDate);
        } catch
        (OrderDataPersistenceException e) {
            fail("Order valid, no exception should be thrown.");
        }

        assertNotNull(updatedOrder);
        assertEquals(2, updatedOrder.getOrderNumber(), "Order number should be 2.");
        assertEquals("B", updatedOrder.getCustomerName(), "Customer name should be B.");
        assertEquals("UT", updatedOrder.getStateAbbreviation(), "State abbreviation should be UT");
        assertEquals("CARPET", updatedOrder.getProductType(), "Product type should be CARPET.");
        assertEquals(testArea, updatedOrder.getArea(), "Area should be 200.");
    }

    @Test
    void orderCalculations() throws TaxDataPersistenceException, ProductNotCurrentlySoldException,
            ProductDataPersistenceException, IOException, FlooringMasteryPersistenceException, StateSalesNotAllowedException {
        String newDate = "05252022";
        Order newOrder = new Order(2);
        newOrder.setCustomerName("C");
        newOrder.setStateAbbreviation("UT");
        BigDecimal bdArea = new BigDecimal("200.00");
        newOrder.setArea(bdArea);
        newOrder.setProductType("CARPET");
        Tax newTax = service.getTaxIfValidStateElseNull(newOrder.getStateAbbreviation());
        Product newProduct = service.getProductIfValidElseNull(newOrder.getProductType());
        //tested values that should result from orderCalculations()
        BigDecimal testArea = new BigDecimal("200.00");
        BigDecimal testTaxRate = new BigDecimal("3.50");
        BigDecimal testCpSF = new BigDecimal("2.75");
        BigDecimal testLCpSF = new BigDecimal("3.50");
        BigDecimal testMC = new BigDecimal("550.00");
        BigDecimal testLC = new BigDecimal("700.00");
        BigDecimal testTax = new BigDecimal("43.75");
        BigDecimal testTotal = new BigDecimal("1293.75");

        Order addedOrder = null;
        try {
            addedOrder = service.addOrder(newOrder, newTax, newProduct, newDate);
        } catch
        (OrderDataPersistenceException e) {
            fail("Order valid, no exception should be thrown.");
        }

        //confirms the following methods from Service as all used in calculateOrder:
        //orderCalculations, addBigDecimal, multiplyBigDecimal, divideBigDecimal
        //getTaxIfValidElseNull, getProductIfValidElseNull
        assertEquals(2, addedOrder.getOrderNumber(), "Order number should be 1.");
        assertEquals("C", addedOrder.getCustomerName(), "Customer name should be C.");
        assertEquals("UT", addedOrder.getStateAbbreviation(), "State abbreviation should be UT");
        assertEquals("CARPET", addedOrder.getProductType(), "Product type should be CARPET.");
        assertEquals(testArea, addedOrder.getArea(), "Area should be 200.");
        assertEquals(testTaxRate, addedOrder.getTaxRate(), "Tax rate should be 2.75");
        assertEquals(testCpSF, addedOrder.getCostPerSquareFoot(), "CpSF should be 3.50.");
        assertEquals(testLCpSF, addedOrder.getLaborCostPerSquareFoot(), "LCpSF should be 3.50.");
        assertEquals(testMC, addedOrder.getMaterialCost(), "MC should be 550.00");
        assertEquals(testLC, addedOrder.getLaborCost(), "LC should be 750.00.");
        assertEquals(testTax, addedOrder.getTax(), "Tax should be 43.75.");
        assertEquals(testTotal, addedOrder.getTotal(), "Invoice total should be 1293.75.");
    }

    @Test
    void getTaxIfValidElseNull() throws TaxDataPersistenceException {
        Order order1 = new Order(1);
        order1.setStateAbbreviation("TX");
        Tax order1Tax = service.getTaxIfValidStateElseNull(order1.getStateAbbreviation());
        BigDecimal order1TaxRate = order1Tax.getTaxRate();
        order1TaxRate = order1TaxRate.setScale(2, RoundingMode.HALF_UP);

        //TX a valid state, should return Tax object with 4.45 tax rate
        assertNotNull(order1Tax);
        BigDecimal txTaxRate = new BigDecimal(4.45);
        assertEquals(order1TaxRate, order1Tax.getTaxRate(), "Texas tax rate should be 4.45");

        Order order2 = new Order(2);
        order1.setStateAbbreviation("LA");
        Tax order2Tax = service.getTaxIfValidStateElseNull(order1.getStateAbbreviation());
        //LA not a valid state, returns NULL
        assertNull(order2Tax, "LA not a valid state, should return NULL");
    }

    @Test
    void getProductIfValidElseNull() throws ProductNotCurrentlySoldException, ProductDataPersistenceException {
        Order order1 = new Order(1);
        order1.setProductType("Tile");
        Product product = service.getProductIfValidElseNull(order1.getProductType());
        String productType = product.getProductType();

        //Tile is a valid product type, should return Product object of type Tile
        assertNotNull(product);
        assertEquals(productType, order1.getProductType(), "Product type should be Tile.");

        Order order2 = new Order(2);
        order2.setProductType("Test");
        Product product2 = service.getProductIfValidElseNull(order2.getProductType());

        //"Test" is not a valid product type, should return null
        assertNull(product2);
    }

}
