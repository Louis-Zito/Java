package dao;

import dto.InventoryItem;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

public class VendingMachineDaoFileImpl implements VendingMachineDao{

    public String vending_machine_inventory;
    public static final String DELIMITER = "::";

    public VendingMachineDaoFileImpl(){
        this.vending_machine_inventory = "vendingInventory.txt";
    }

//    second constructor used in JUnit test calls
//    argument for new text file avoids overwriting actual inventory txt file
    public VendingMachineDaoFileImpl(String daoStorageFile){
        this.vending_machine_inventory = daoStorageFile;
    }

    private Map<String, InventoryItem> currentInventory = new HashMap<>();

    @Override
    public InventoryItem createItem(String itemName, InventoryItem item) throws VendingMachinePersistenceException {
        loadInventoryFile();
        InventoryItem newItem = currentInventory.put(itemName, item);
        writeInventoryFile();
        return newItem;
    }

    @Override
    public InventoryItem readByName(String itemName) throws VendingMachinePersistenceException{
        loadInventoryFile();
        InventoryItem selection = currentInventory.get(itemName);
        return selection;
    }

    @Override
    public List<InventoryItem> readAll() throws VendingMachinePersistenceException {
        loadInventoryFile();
        return new ArrayList<>(currentInventory.values());
    }

    @Override
    public InventoryItem updateItem(InventoryItem item) throws VendingMachinePersistenceException{
        loadInventoryFile();
        InventoryItem updatedItem = new InventoryItem(item.getItemName());
        updatedItem.setItemCost(item.getItemCost());
        updatedItem.setNumberInInventory(item.getNumberInInventory());
        currentInventory.put(updatedItem.getItemName(), updatedItem);
        writeInventoryFile();
        return updatedItem;
    }


    @Override
    public InventoryItem deleteItem(String itemName) throws VendingMachinePersistenceException{
        loadInventoryFile();
        InventoryItem removedItem = currentInventory.remove(itemName);
        writeInventoryFile();
        return removedItem;
    }

    private InventoryItem unmarshallInventory(String inventoryItemAsText){
        String[] inventoryTokens = inventoryItemAsText.split(DELIMITER);
        String itemName = inventoryTokens[0];
        InventoryItem itemFromFile = new InventoryItem(itemName);
        //Convert String from text file to BigDecimal for Unmarshall of object
        BigDecimal costFromFile = new BigDecimal(inventoryTokens[1]);
        itemFromFile.setItemCost(costFromFile);
        itemFromFile.setNumberInInventory(Integer.parseInt(inventoryTokens[2]));
        return itemFromFile;
    }

    private void loadInventoryFile() throws VendingMachinePersistenceException{
        Scanner scanner;
        try{
            scanner = new Scanner(new BufferedReader(new FileReader(vending_machine_inventory)));
        } catch (FileNotFoundException e){
            throw new VendingMachinePersistenceException("-_- Could not load inventory file into memory.", e);
        }
        String currentLine;
        InventoryItem currentItem;
        while (scanner.hasNextLine()){
            currentLine = scanner.nextLine();
            currentItem = unmarshallInventory(currentLine);
            currentInventory.put(currentItem.getItemName(), currentItem);
        }//end while
    }//end loadInventoryFile

    String marshallInventory(InventoryItem anItem){
        String itemAsText = anItem.getItemName() + DELIMITER;
        itemAsText += anItem.getItemCost() + DELIMITER;
        itemAsText += anItem.getNumberInInventory();
        return itemAsText;
    }

    public void writeInventoryFile() throws VendingMachinePersistenceException{
        PrintWriter out;
        try{
            out = new PrintWriter(new FileWriter(vending_machine_inventory));
        } catch (IOException e){
            throw new VendingMachinePersistenceException("Could not save inventory data.");
        }

        String itemAsText;
        List<InventoryItem> inventoryList = this.readAll();
        for (InventoryItem item : inventoryList){
            itemAsText = marshallInventory(item);
            out.println(itemAsText);
            out.flush();
        }
        out.close();

    }//end writeInventoryFile
}//end class

