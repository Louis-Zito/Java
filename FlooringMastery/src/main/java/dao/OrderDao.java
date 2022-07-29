package dao;
import dto.Order;

import java.io.IOException;
import java.util.List;

public interface OrderDao {

    Order createOrder(Order order, String date) throws OrderDataPersistenceException, IOException;

    List<Order> readAllOrders(String date) throws OrderDataPersistenceException;

    Order readByDateAndOrderNumber(int orderNumber, String date) throws OrderDataPersistenceException;

    Order updateOrder(Order order, String date) throws OrderDataPersistenceException;

    Order deleteOrder(int orderNumber, String date) throws OrderDataPersistenceException;

}
