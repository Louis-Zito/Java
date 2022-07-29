package dao;

import dto.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FlooringMasteryDaoFileImplTest {

    OrderDao testOrderDao;

    public FlooringMasteryDaoFileImplTest() throws OrderDataPersistenceException{
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testOrderDao = ctx.getBean("orderDao", OrderDao.class);
    }

//    @BeforeEach
//    public void setup(){
//        String folder = "TestFolder\\";
//        testOrderDao = new OrderDaoFileImpl(folder);
//    }

    @Test
    void createOrder() throws Exception{
        String orderDate = "05252022";
        Order newOrder = new Order(1);
        newOrder.setCustomerName("Louis");
        newOrder.setStateAbbreviation("TX");
        newOrder.setProductType("TILE");
        BigDecimal bdTaxRate = new BigDecimal("4.5");
        newOrder.setTaxRate(bdTaxRate);
        BigDecimal bdArea = new BigDecimal("100.00");
        newOrder.setArea(bdArea);
        BigDecimal CpSF = new BigDecimal("150.00");
        newOrder.setCostPerSquareFoot(CpSF);
        BigDecimal LCpSF = new BigDecimal("200.00");
        newOrder.setLaborCostPerSquareFoot(LCpSF);
        BigDecimal MC = new BigDecimal("250.00");
        newOrder.setMaterialCost(MC);
        BigDecimal LC = new BigDecimal("300.00");
        newOrder.setLaborCost(LC);
        BigDecimal tax = new BigDecimal("350.00");
        newOrder.setTax(tax);
        BigDecimal total = new BigDecimal("400.00");
        newOrder.setTotal(total);
        testOrderDao.createOrder(newOrder, orderDate);

        Order testOrder = testOrderDao.readByDateAndOrderNumber(1, orderDate);

        assertEquals(newOrder.getOrderNumber(), testOrder.getOrderNumber(), "Testing order number.");
        assertEquals(newOrder.getCustomerName(), testOrder.getCustomerName(), "Testing customer name.");
        assertEquals(newOrder.getStateAbbreviation(), testOrder.getStateAbbreviation(), "Testing state abbreviation.");
        assertEquals(newOrder.getProductType(), testOrder.getProductType(), "Testing product type");
        assertEquals(newOrder.getArea(), testOrder.getArea(), "Testing area.");
    }

    @Test
    void readAllOrders() throws Exception{
        String orderDate = "05252022";

        Order newOrder2 = new Order(2); newOrder2.setCustomerName("Two"); newOrder2.setStateAbbreviation("TX");
        newOrder2.setProductType("TILE"); BigDecimal bdTaxRate2 = new BigDecimal("4.5"); newOrder2.setTaxRate(bdTaxRate2);
        BigDecimal bdArea2 = new BigDecimal("100.00"); newOrder2.setArea(bdArea2); BigDecimal CpSF2 = new BigDecimal("150.00");
        newOrder2.setCostPerSquareFoot(CpSF2); BigDecimal LCpSF2 = new BigDecimal("200.00"); newOrder2.setLaborCostPerSquareFoot(LCpSF2);
        BigDecimal MC2 = new BigDecimal("250.00"); newOrder2.setMaterialCost(MC2); BigDecimal LC2 = new BigDecimal("300.00");
        newOrder2.setLaborCost(LC2); BigDecimal tax2 = new BigDecimal("350.00"); newOrder2.setTax(tax2);
        BigDecimal total2 = new BigDecimal("400.00"); newOrder2.setTotal(total2);
        testOrderDao.createOrder(newOrder2, orderDate);

        Order newOrder3 = new Order(3); newOrder3.setCustomerName("Three"); newOrder3.setStateAbbreviation("TX");
        newOrder3.setProductType("TILE"); BigDecimal bdTaxRate3 = new BigDecimal("4.5"); newOrder3.setTaxRate(bdTaxRate3);
        BigDecimal bdArea3 = new BigDecimal("100.00"); newOrder3.setArea(bdArea3); BigDecimal CpSF3 = new BigDecimal("150.00");
        newOrder3.setCostPerSquareFoot(CpSF3); BigDecimal LCpSF3 = new BigDecimal("200.00"); newOrder3.setLaborCostPerSquareFoot(LCpSF3);
        BigDecimal MC3 = new BigDecimal("250.00"); newOrder3.setMaterialCost(MC3); BigDecimal LC3 = new BigDecimal("300.00");
        newOrder3.setLaborCost(LC3); BigDecimal tax3 = new BigDecimal("350.00"); newOrder3.setTax(tax3);
        BigDecimal total3 = new BigDecimal("400.00"); newOrder3.setTotal(total3);
        testOrderDao.createOrder(newOrder3, orderDate);

        Order newOrder4 = new Order(4); newOrder4.setCustomerName("Four"); newOrder4.setStateAbbreviation("TX");
        newOrder4.setProductType("TILE"); BigDecimal bdTaxRate4 = new BigDecimal("4.5"); newOrder4.setTaxRate(bdTaxRate4);
        BigDecimal bdArea4 = new BigDecimal("100.00"); newOrder4.setArea(bdArea4); BigDecimal CpSF4 = new BigDecimal("150.00");
        newOrder4.setCostPerSquareFoot(CpSF4); BigDecimal LCpSF4 = new BigDecimal("200.00"); newOrder4.setLaborCostPerSquareFoot(LCpSF4);
        BigDecimal MC4 = new BigDecimal("250.00"); newOrder4.setMaterialCost(MC4); BigDecimal LC4 = new BigDecimal("300.00");
        newOrder4.setLaborCost(LC4); BigDecimal tax4 = new BigDecimal("350.00"); newOrder4.setTax(tax4);
        BigDecimal total4 = new BigDecimal("400.00"); newOrder4.setTotal(total4);
        testOrderDao.createOrder(newOrder4, orderDate);

        List<Order> allOrders = testOrderDao.readAllOrders(orderDate);

        assertNotNull(allOrders, "List should not be null");
        assertEquals(3, allOrders.size(), "List should be size 3");
        assertTrue(testOrderDao.readAllOrders(orderDate).contains(newOrder2), "The list included order 2" );
        assertTrue(testOrderDao.readAllOrders(orderDate).contains(newOrder3), "The list included order 3" );
        assertTrue(testOrderDao.readAllOrders(orderDate).contains(newOrder4), "The list included order 4" );
    }

    @Test
    void updateOrder() throws Exception{
        String orderDate = "05252022";

        Order newOrder5 = new Order(5); newOrder5.setCustomerName("FIVE"); newOrder5.setStateAbbreviation("TX");
        newOrder5.setProductType("TILE"); BigDecimal bdTaxRate5 = new BigDecimal("4.5"); newOrder5.setTaxRate(bdTaxRate5);
        BigDecimal bdArea5 = new BigDecimal("100.00"); newOrder5.setArea(bdArea5); BigDecimal CpSF5 = new BigDecimal("150.00");
        newOrder5.setCostPerSquareFoot(CpSF5); BigDecimal LCpSF5 = new BigDecimal("200.00"); newOrder5.setLaborCostPerSquareFoot(LCpSF5);
        BigDecimal MC5 = new BigDecimal("250.00"); newOrder5.setMaterialCost(MC5); BigDecimal LC5 = new BigDecimal("300.00");
        newOrder5.setLaborCost(LC5); BigDecimal tax5 = new BigDecimal("350.00"); newOrder5.setTax(tax5);
        BigDecimal total5 = new BigDecimal("400.00"); newOrder5.setTotal(total5);
        testOrderDao.createOrder(newOrder5, orderDate);

        assertEquals("FIVE", newOrder5.getCustomerName());
        assertEquals(bdTaxRate5, newOrder5.getTaxRate());

        newOrder5.setCustomerName("UPDATED");
        BigDecimal newTaxRate = new BigDecimal("3.0");
        newOrder5.setTaxRate(newTaxRate);

        testOrderDao.updateOrder(newOrder5, orderDate);
        assertEquals("UPDATED", newOrder5.getCustomerName());
        BigDecimal updatedTaxRate = new BigDecimal("3.0");
        assertEquals(updatedTaxRate, newOrder5.getTaxRate());
    }

    @Test
    void deleteOrder() throws Exception {
        String orderDate = "05252022";

        Order newOrder6 = new Order(6); newOrder6.setCustomerName("SIX"); newOrder6.setStateAbbreviation("TX");
        newOrder6.setProductType("TILE"); BigDecimal bdTaxRate6 = new BigDecimal("4.5"); newOrder6.setTaxRate(bdTaxRate6);
        BigDecimal bdArea6 = new BigDecimal("100.00"); newOrder6.setArea(bdArea6); BigDecimal CpSF6 = new BigDecimal("150.00");
        newOrder6.setCostPerSquareFoot(CpSF6); BigDecimal LCpSF6 = new BigDecimal("200.00"); newOrder6.setLaborCostPerSquareFoot(LCpSF6);
        BigDecimal MC6 = new BigDecimal("250.00"); newOrder6.setMaterialCost(MC6); BigDecimal LC6 = new BigDecimal("300.00");
        newOrder6.setLaborCost(LC6); BigDecimal tax6 = new BigDecimal("350.00"); newOrder6.setTax(tax6);
        BigDecimal total6 = new BigDecimal("400.00"); newOrder6.setTotal(total6);
        testOrderDao.createOrder(newOrder6, orderDate);

        Order newOrder7 = new Order(7); newOrder7.setCustomerName("SEVEN"); newOrder7.setStateAbbreviation("TX");
        newOrder7.setProductType("TILE"); BigDecimal bdTaxRate7 = new BigDecimal("4.5"); newOrder7.setTaxRate(bdTaxRate7);
        BigDecimal bdArea7 = new BigDecimal("100.00"); newOrder7.setArea(bdArea7); BigDecimal CpSF7 = new BigDecimal("150.00");
        newOrder7.setCostPerSquareFoot(CpSF7); BigDecimal LCpSF7 = new BigDecimal("200.00"); newOrder7.setLaborCostPerSquareFoot(LCpSF7);
        BigDecimal MC7 = new BigDecimal("250.00"); newOrder7.setMaterialCost(MC7); BigDecimal LC7 = new BigDecimal("300.00");
        newOrder7.setLaborCost(LC7); BigDecimal tax7 = new BigDecimal("350.00"); newOrder7.setTax(tax7);
        BigDecimal total7 = new BigDecimal("400.00"); newOrder7.setTotal(total7);
        testOrderDao.createOrder(newOrder7, orderDate);

        Order removedOrder6 = testOrderDao.deleteOrder(6, orderDate);
        assertEquals(newOrder6, removedOrder6, "Removed order was Order 6");
        List<Order> allOrders = testOrderDao.readAllOrders(orderDate);
        assertEquals(1, allOrders.size(), "Order hash map should have 1 order after removal");
        Order removedOrder7 = testOrderDao.deleteOrder(7, orderDate);
        assertEquals(newOrder7, removedOrder7, "Removed order was Order 7");
        allOrders = testOrderDao.readAllOrders(orderDate);
        assertTrue(allOrders.isEmpty(), "The list of Orders should now be empty after 2nd removeOrder");
    }





















































}//end class
