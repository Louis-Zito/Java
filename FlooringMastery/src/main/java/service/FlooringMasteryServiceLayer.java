package service;

import dao.FlooringMasteryPersistenceException;
import dao.OrderDataPersistenceException;
import dao.ProductDataPersistenceException;
import dao.TaxDataPersistenceException;
import dto.Order;
import dto.Product;
import dto.Tax;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface FlooringMasteryServiceLayer {

    public String editDateStringForDao(String date);

    List<Order> getDaysOrders(String date) throws OrderDataPersistenceException;

    public Order addOrder(Order order, Tax tax, Product product, String date) throws OrderDataPersistenceException, TaxDataPersistenceException, StateSalesNotAllowedException, IOException, FlooringMasteryPersistenceException;

    public Order editOrder(Order order, String date) throws OrderDataPersistenceException, FlooringMasteryPersistenceException;

    public boolean checkEditOrderValuesForRecalculation(Order originalOrder, Order orderWithEdits);

    public Order removeOrder(Order order, String date) throws OrderDataPersistenceException, FlooringMasteryPersistenceException;

    public Order getOrderIfExistingOrder(int orderNumber, String date) throws OrderDataPersistenceException;

    public List<Tax> getCurrentTaxObjects() throws TaxDataPersistenceException;

    public Tax getTaxIfValidStateElseNull(String state) throws TaxDataPersistenceException;

    public List<Product> getCurrentProductObjects() throws ProductDataPersistenceException;

    public Product getProductIfValidElseNull(String product) throws ProductNotCurrentlySoldException, ProductDataPersistenceException;

    public Order orderCalculations(Order order, Tax tax, Product product);

    public int getOrderNumber(String date) throws OrderDataPersistenceException;

    public BigDecimal addBigDecimal(BigDecimal x, BigDecimal y);

    public BigDecimal multiplyBigDecimal(BigDecimal x, BigDecimal y);

    public BigDecimal divideBigDecimal(BigDecimal divisor, BigDecimal dividend);
}
