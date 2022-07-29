package dao;
import dto.Order;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class OrderDaoFileImpl implements OrderDao{

    public static final String ORDERS_FOLDER = "Orders\\";
    public static final String DELIMITER = ",";
    public String orderFileName;
    public String folder;
    public static final String HEADER = "Order,Name,State,Tax-Rate,Product,Area,Cost-SqFt,LaborCost-SqFt,Material,Labor,Tax,TOTAL";

    public OrderDaoFileImpl(){
        this.folder = ORDERS_FOLDER;
    }

    //Overloaded for JUnit test constructor
    public OrderDaoFileImpl(String folder){
        this.folder = "TestFolder\\";
    }

    private Map<Integer, Order> dayOfOrders = new HashMap<>();

    @Override
    public Order createOrder(Order order, String date) throws OrderDataPersistenceException, IOException {
        orderFileName = "Orders_" + date + ".txt";
        File directory = new File(folder);
        try {
            File newFile = new File(directory, orderFileName);
            if(newFile.createNewFile()){
                System.out.println("New order date, file created: " + newFile.getName());
            }
        } catch (IOException e){
            throw new IOException("New order file could not be created", e);
        }
        dayOfOrders.put(order.getOrderNumber(), order);
        writeOrderFile(orderFileName);
        return order;
    }

    @Override
    public List<Order> readAllOrders(String date) throws OrderDataPersistenceException {
        orderFileName = "Orders_" + date + ".txt";
        loadOrderFile(orderFileName);
        return new ArrayList<>(dayOfOrders.values());
    }

    @Override
    public Order readByDateAndOrderNumber(int orderNumber, String date) throws OrderDataPersistenceException {
        orderFileName = "Orders_" + date + ".txt";
        loadOrderFile(orderFileName);
        Order order = dayOfOrders.get(orderNumber);
        return order;
    }

    @Override
    public Order updateOrder(Order order, String date) throws OrderDataPersistenceException {
        orderFileName = "Orders_" + date + ".txt";
        loadOrderFile(orderFileName);
        Order updatedOrder = new Order(order.getOrderNumber());
        updatedOrder.setCustomerName(order.getCustomerName());
        updatedOrder.setStateAbbreviation(order.getStateAbbreviation());
        updatedOrder.setTaxRate(order.getTaxRate());
        updatedOrder.setProductType(order.getProductType());
        updatedOrder.setArea(order.getArea());
        updatedOrder.setCostPerSquareFoot(order.getCostPerSquareFoot());
        updatedOrder.setLaborCostPerSquareFoot(order.getLaborCostPerSquareFoot());
        updatedOrder.setMaterialCost(order.getMaterialCost());
        updatedOrder.setLaborCost(order.getLaborCost());
        updatedOrder.setTax(order.getTax());
        updatedOrder.setTotal(order.getTotal());
        dayOfOrders.put(order.getOrderNumber(), updatedOrder);
        writeOrderFile(orderFileName);
        return updatedOrder;
    }


    @Override
    public Order deleteOrder(int orderNumber, String date) throws OrderDataPersistenceException {
        orderFileName = "Orders_" + date + ".txt";
        loadOrderFile(orderFileName);
        Order removedOrder = dayOfOrders.remove(orderNumber);
        writeOrderFile(orderFileName);
        return removedOrder;
    }

    private Order unmarshallOrder(String orderDataAsText){
        String[] orderTokens = orderDataAsText.split(DELIMITER);
        Integer orderNumber = Integer.valueOf(orderTokens[0]);
        Order orderFromFile = new Order(orderNumber);
        orderFromFile.setCustomerName(orderTokens[1]);
        orderFromFile.setStateAbbreviation(orderTokens[2]);
        BigDecimal taxRateToBigDecimal = new BigDecimal(orderTokens[3]);
        orderFromFile.setTaxRate(taxRateToBigDecimal);
        orderFromFile.setProductType(orderTokens[4]);
        BigDecimal areaToBigDecimal = new BigDecimal(orderTokens[5]);
        orderFromFile.setArea(areaToBigDecimal);
        BigDecimal costPerSqFtToBigDecimal = new BigDecimal(orderTokens[6]);
        orderFromFile.setCostPerSquareFoot(costPerSqFtToBigDecimal);
        BigDecimal laborCostPerSqFtToBigDecimal = new BigDecimal(orderTokens[7]);
        orderFromFile.setLaborCostPerSquareFoot(laborCostPerSqFtToBigDecimal);
        BigDecimal materialCostToBigDecimal = new BigDecimal(orderTokens[8]);
        orderFromFile.setMaterialCost(materialCostToBigDecimal);
        BigDecimal laborCostToBigDecimal = new BigDecimal(orderTokens[9]);
        orderFromFile.setLaborCost(laborCostToBigDecimal);
        BigDecimal taxToBigDecimal = new BigDecimal(orderTokens[10]);
        orderFromFile.setTax(taxToBigDecimal);
        BigDecimal totalToBigDecimal = new BigDecimal(orderTokens[11]);
        orderFromFile.setTotal(totalToBigDecimal);
        return orderFromFile;
    }

        private void loadOrderFile(String orderFileName) throws OrderDataPersistenceException{
            orderFileName = folder + orderFileName;
            Scanner scanner;
            try{
                scanner = new Scanner(new BufferedReader(new FileReader(orderFileName)));
            } catch (FileNotFoundException e){
                throw new OrderDataPersistenceException("No orders exist for this date.");
            }
            String currentLine;
            Order order;
            while(scanner.hasNextLine()){
                currentLine = scanner.nextLine();
                if (currentLine.equals(HEADER)) {
                    continue;
                }
                order = unmarshallOrder(currentLine);
                dayOfOrders.put(order.getOrderNumber(), order);
            }
        }

        String marshallOrder(Order order){
            String orderAsText = order.getOrderNumber() + DELIMITER;
            orderAsText += order.getCustomerName() + DELIMITER;
            orderAsText += order.getStateAbbreviation() + DELIMITER;
            orderAsText += order.getTaxRate() + DELIMITER;
            orderAsText += order.getProductType() + DELIMITER;
            orderAsText += order.getArea() + DELIMITER;
            orderAsText += order.getCostPerSquareFoot() + DELIMITER;
            orderAsText += order.getLaborCostPerSquareFoot() + DELIMITER;
            orderAsText += order.getMaterialCost() + DELIMITER;
            orderAsText += order.getLaborCost() + DELIMITER;
            orderAsText += order.getTax() + DELIMITER;
            orderAsText += order.getTotal();
            return orderAsText;
        }

        public void writeOrderFile(String orderFileName) throws OrderDataPersistenceException{
            int x;
            orderFileName = folder + orderFileName;
            PrintWriter out;
            try{
                out = new PrintWriter(new BufferedWriter(new FileWriter(orderFileName)));
            } catch (IOException e){
                throw new OrderDataPersistenceException("writeOrderFile Ex: Order file could not be saved.", e);
            }
            String orderAsText;
            List<Order> orderList = new ArrayList(dayOfOrders.values());
            for (x = 0; x < orderList.size(); x++){
                if (x == 0){
                    out.println(HEADER);
                }
                orderAsText = marshallOrder(orderList.get(x));
                out.println(orderAsText);
                out.flush();
            }
            out.close();
        }
    }

