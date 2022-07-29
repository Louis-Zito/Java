package dao;
import dto.Product;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class ProductDaoFileImpl implements ProductDao{

    public String productData;
    public static final String DELIMITER = ",";

    public ProductDaoFileImpl(){
        this.productData = "Data/Products.txt";
    }

    public ProductDaoFileImpl(String productsStorageFileName){
        this.productData = productsStorageFileName;
    }

    private Map<String, Product> currentProducts = new HashMap<>();

    @Override
    public Product createProduct(String productType, Product product) throws ProductDataPersistenceException {
        loadProductFile();
        Product newProduct = currentProducts.put(productType, product);
        writeProductFile();
        return newProduct;
    }

    @Override
    public List<Product> readAllProducts() throws ProductDataPersistenceException {
        loadProductFile();
        return new ArrayList<>(currentProducts.values());
    }

    @Override
    public Product readByName(String productType) throws ProductDataPersistenceException {
        loadProductFile();
        Product selectedProduct = currentProducts.get(productType);
        return selectedProduct;
    }

    @Override
    public Product updateProduct(Product product) throws ProductDataPersistenceException {
        loadProductFile();
        Product updatedProduct = new Product(product.getProductType());
        updatedProduct.setCostPerSquareFoot(updatedProduct.getCostPerSquareFoot());
        updatedProduct.setLaborCostPerSquareFoot(updatedProduct.getLaborCostPerSquareFoot());
        currentProducts.put(updatedProduct.getProductType(), updatedProduct);
        writeProductFile();
        return updatedProduct;
    }

    @Override
    public Product deleteProduct(String productType) throws ProductDataPersistenceException {
        loadProductFile();
        Product removedProduct = currentProducts.remove(productType);
        writeProductFile();
        return removedProduct;
    }

    private Product unmarshallProductData(String productDataAsText){
        String[] productTokens = productDataAsText.split(DELIMITER);
        String productType = productTokens[0];
        Product productItemFromFile = new Product(productType);
        BigDecimal costPerSquareFoot = new BigDecimal(productTokens[1]);
        productItemFromFile.setCostPerSquareFoot(costPerSquareFoot);
        BigDecimal laborCostPerSquareFoot = new BigDecimal(productTokens[2]);
        productItemFromFile.setLaborCostPerSquareFoot(laborCostPerSquareFoot);
        return productItemFromFile;
    }

    private void loadProductFile() throws ProductDataPersistenceException{
        Scanner scanner;
        try{
            scanner = new Scanner(new BufferedReader(new FileReader(productData)));
        } catch (FileNotFoundException e){
            throw new ProductDataPersistenceException("Could not load Product data into memory.", e);
        }
        String currentLine;
        Product productData;
        while(scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            productData = unmarshallProductData(currentLine);
            currentProducts.put(productData.getProductType(), productData);
        }
    }

    String marshallProductData(Product productData){
        String productAsText = productData.getProductType() + DELIMITER;
        productAsText += productData.getCostPerSquareFoot() + DELIMITER;
        productAsText += productData.getLaborCostPerSquareFoot();
        return productAsText;
    }

    public void writeProductFile() throws ProductDataPersistenceException{
        PrintWriter out;
        try{
            out = new PrintWriter(new FileWriter(productData));
        } catch(IOException e){
            throw new ProductDataPersistenceException("Could not save Product data.", e);
        }
        String productAsText;
        List<Product> productList = this.readAllProducts();
        for (Product productType : productList){
            productAsText = marshallProductData(productType);
            out.println(productAsText);
            out.flush();
        }
        out.close();
    }
}
