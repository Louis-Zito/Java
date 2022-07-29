package service;

import dao.OrderDao;
import dao.OrderDaoFileImpl;
import dao.OrderDataPersistenceException;
import dto.Order;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FlooringMasteryOrderDaoStubImpl implements OrderDao {

    OrderDao testOrderDao;

    public FlooringMasteryOrderDaoStubImpl() throws OrderDataPersistenceException {
        String folder = "TestFolder\\";
        testOrderDao = new OrderDaoFileImpl(folder);
    }

    @Override
    public Order createOrder(Order order, String date) throws OrderDataPersistenceException, IOException {
        return order;
    }

    @Override
    public List<Order> readAllOrders(String date) throws OrderDataPersistenceException {
        List<Order> singleOrderForDay = new ArrayList<>();
        Order onlyOrder = new Order(1);
        singleOrderForDay.add(onlyOrder);
        return singleOrderForDay;

    }

    @Override
    public Order readByDateAndOrderNumber(int orderNumber, String date) throws OrderDataPersistenceException {
        Order order = new Order(orderNumber);
        return order;
    }

    @Override
    public Order updateOrder(Order order, String date) throws OrderDataPersistenceException {
        if (order.equals(order)){
            return order;
        }
        else{
            return null;
        }
    }

    @Override
    public Order deleteOrder(int orderNumber, String date) throws OrderDataPersistenceException {
        Order deletedOrder = new Order(orderNumber);
        return deletedOrder;
    }
}
