package service;

import dao.*;
import dto.Order;
import dto.Product;
import dto.Tax;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class FlooringMasterServiceLayerImpl implements FlooringMasteryServiceLayer{

    private OrderDao orderDao;
    private ProductDao productDao;
    private TaxDao taxDao;
    private FlooringMasteryAuditDao FMADao;

    private OrderDao orderDao(String date) {
        return orderDao;
    }

    public FlooringMasterServiceLayerImpl(OrderDao orderDao, ProductDao productDao, TaxDao taxDao, FlooringMasteryAuditDao FMADao){
        this.orderDao = orderDao;
        this.productDao = productDao;
        this.taxDao = taxDao;
        this.FMADao = FMADao;
    }

    @Override
    public List<Order> getDaysOrders(String date) throws OrderDataPersistenceException {
        date = editDateStringForDao(date);
        List<Order> dayOfOrders = orderDao.readAllOrders(date);
        if (dayOfOrders == null){
            throw new OrderDataPersistenceException("Orders do not exist for this date.\n");
        }
        return dayOfOrders;
        }

    @Override
    public Order addOrder(Order order, Tax tax, Product product, String date) throws OrderDataPersistenceException, IOException, FlooringMasteryPersistenceException {
        date = editDateStringForDao(date);
        order = orderCalculations(order, tax, product);
        int orderNumber = getOrderNumber(date);
        order.setOrderNumber(orderNumber);
        Order newOrder = orderDao.createOrder(order, date);
        FMADao.writeAuditEntry("Order edited: Date - " + date + ", Order No. - " + newOrder.getOrderNumber() + " ADDED");
        return newOrder;
    }

    @Override
    //if order number not found, null returned from "getOrderIfExistingOrder", throw Exception
    //if order number found, update, return updated order for View
    public Order editOrder(Order order, String date) throws OrderDataPersistenceException, FlooringMasteryPersistenceException {
        date = editDateStringForDao(date);
        Order updatedOrder = orderDao.updateOrder(order, date);
        System.out.println("Edits accepted and being saved to order " + order.getOrderNumber());
        FMADao.writeAuditEntry("Order edited: Date - " + date + ", Order No. - " + updatedOrder.getOrderNumber() + " EDITED.");
        return updatedOrder;
    }

    @Override
    //if originalOrder doesn't match orderWithEdits: state/product/area, return True for recalculations
    public boolean checkEditOrderValuesForRecalculation(Order originalOrder, Order orderWithEdits){
        if (      (! (orderWithEdits.getStateAbbreviation().equals(originalOrder.getStateAbbreviation())))
                | (! (orderWithEdits.getProductType().equals(originalOrder.getProductType()            )))
                | (! (orderWithEdits.getArea().equals(originalOrder.getArea())                  ))){
            return true;
        }
        else return false;
    }

    @Override
    public Order removeOrder(Order order, String date) throws OrderDataPersistenceException, FlooringMasteryPersistenceException {
        date = editDateStringForDao(date);
        Order removedOrder = orderDao.deleteOrder(order.getOrderNumber(), date);
        FMADao.writeAuditEntry("Order edited: Date - " + date + ", Order No. - " + removedOrder.getOrderNumber() + " REMOVED.");
        return removedOrder;
    }

    @Override
    //search OrderDao(Date) for existing order number
    //if found return order, else return null
    public Order getOrderIfExistingOrder(int desiredOrderNumber, String date) throws OrderDataPersistenceException{
        date = editDateStringForDao(date);
        List<Order> daysOrders = orderDao.readAllOrders(date);
        Order searchedOrder = null;
        for(Order order : daysOrders){
            if (order.getOrderNumber() == desiredOrderNumber){
                searchedOrder = order;
            }
        }
        return searchedOrder;
    }

    @Override
    public Tax getTaxIfValidStateElseNull(String state) throws TaxDataPersistenceException {
        //return Tax object if state found or Null if not a valid state
        List<Tax> allStateTaxes = taxDao.readAllTaxes();
        for (Tax stateTax : allStateTaxes){
            if (stateTax.getStateAbbreviation().equals(state)){
                return stateTax;
            }
        }
        return null;
    }

    @Override
    public List<Tax> getCurrentTaxObjects() throws TaxDataPersistenceException {
        List<Tax> currentTaxObjects = taxDao.readAllTaxes();
        return currentTaxObjects;
    }

    @Override
    public Product getProductIfValidElseNull(String product) throws ProductDataPersistenceException {
        //return Product object if found or Null if not a sold product
        List<Product> allProducts = productDao.readAllProducts();
        for (Product theProduct : allProducts){
            if (theProduct.getProductType().equals(product)){
                return theProduct;
            }
        }
        return null;
    }

    @Override
    public List<Product> getCurrentProductObjects() throws ProductDataPersistenceException {
        List<Product> currentProductObjects = productDao.readAllProducts();
        return currentProductObjects;
    }

    @Override
    public Order orderCalculations(Order order, Tax tax, Product product) {
        BigDecimal oneHundred = new BigDecimal(100);
        BigDecimal materialCost = multiplyBigDecimal(order.getArea(), product.getCostPerSquareFoot());
        BigDecimal laborCost = multiplyBigDecimal(order.getArea(), product.getLaborCostPerSquareFoot());
        BigDecimal taxPercentage = divideBigDecimal(oneHundred, tax.getTaxRate());
        BigDecimal materialCostPlusLaborCost = addBigDecimal(materialCost, laborCost);
        BigDecimal totalTax = multiplyBigDecimal(materialCostPlusLaborCost, taxPercentage);
        BigDecimal orderTotal = addBigDecimal(materialCostPlusLaborCost, totalTax);
        order.setTaxRate(tax.getTaxRate());
        order.setProductType(product.getProductType());
        order.setCostPerSquareFoot(product.getCostPerSquareFoot());
        order.setLaborCostPerSquareFoot(product.getLaborCostPerSquareFoot());
        order.setMaterialCost(materialCost);
        order.setLaborCost(laborCost);
        order.setTax(totalTax);
        order.setTotal(orderTotal);
        return order;
    }

    @Override
    //getOrderNumber: arg: date as string
    //check for existence of .txt file matching entered date
    //if file exists, make List: if List empty order = 1, else get highest order number in list, return with +1
    //no file exists, order number = 1
    public int getOrderNumber(String date) throws OrderDataPersistenceException {
        int highestOrderNumber = 1;
        date = editDateStringForDao(date);
        String fileNameToCheck = "Orders\\Orders_" + date + ".txt";
        File f = new File(fileNameToCheck);
        if (f.exists()) {
            System.out.println("File exists, retrieving invoice number data.");
            List<Order> ordersForTheDay = orderDao.readAllOrders(date);
            if (ordersForTheDay.isEmpty()) {
                //check for one invoice made, deleted but file still remains
                return highestOrderNumber;
            } else {
                for (Order order : ordersForTheDay) {
                    int currentOrderNumber = order.getOrderNumber();
                    if (currentOrderNumber > highestOrderNumber) {
                        highestOrderNumber = currentOrderNumber;
                    }
                }
                return highestOrderNumber + 1;
            }//inner else
        }//top if
        else {
            //file doesn't exist = order number of 1
            return highestOrderNumber;
        }//top elf
    }//end method

    @Override
    public BigDecimal addBigDecimal(BigDecimal x, BigDecimal y) {
        BigDecimal sum = x.add(y);
        sum = sum.setScale(2, RoundingMode.HALF_UP);
        return sum;
    }

    @Override
    public BigDecimal multiplyBigDecimal(BigDecimal x, BigDecimal y) {
        BigDecimal product = x.multiply(y);
        product = product.setScale(2, RoundingMode.HALF_UP);
        return product;
    }

    @Override
    //divide allows scale:4 to represent full tax/100 values
    public BigDecimal divideBigDecimal(BigDecimal oneHundred, BigDecimal tax) {
        BigDecimal decimalTaxRate =  tax.divide(oneHundred);
        decimalTaxRate = decimalTaxRate.setScale(4, RoundingMode.HALF_UP);
        return decimalTaxRate;
    }

    @Override
    public String editDateStringForDao(String date){
        date = date.replace("/", "");
        return date;
    }
}
