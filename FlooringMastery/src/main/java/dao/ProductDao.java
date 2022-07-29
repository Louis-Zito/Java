package dao;
import java.util.List;
import dto.Product;

public interface ProductDao {

    Product createProduct(String productType, Product product) throws ProductDataPersistenceException;

    List<Product> readAllProducts() throws ProductDataPersistenceException;

    Product readByName(String productType) throws ProductDataPersistenceException;

    Product updateProduct(Product product) throws ProductDataPersistenceException;

    Product deleteProduct(String productType) throws ProductDataPersistenceException;
}
