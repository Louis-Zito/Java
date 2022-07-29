package ui;
import dto.Change;
import dto.InventoryItem;

import java.math.BigDecimal;
import java.util.List;

public class VendingMachineView {

    public VendingMachineView(UserIO io){
        this.io = io;
    }

    private UserIO io;

    public int printMachineOptionsGetSelection(){
        io.print("Menu Options");
        io.print("1. Purchase an item.");
        io.print("2. Exit machine menu.");
        io.print("3. Add inventory item.");
        io.print("4. Remove inventory item.");

        return io.readInt("Please select from the above options", 1, 4);
    }

    public void displayChangeDue(Change returnedChange){
        System.out.println("---Change Due---");
        BigDecimal[] coins = {returnedChange.getDollars(), returnedChange.getQuarters(), returnedChange.getDimes(),
                              returnedChange.getNickles(), returnedChange.getPennies()};
        String[] denominations = {"Dollar(s): ", "Quarter(s): ", "Dime(s): ", "Nickle(s): ", "Pennie(s): "};
        for (int i = 0; i < 5; i ++){
            if(coins[i] == null){
                continue;
            }
            System.out.println(denominations[i] + coins[i]);
        }
    }

    public void displayInventoryBanner(){
        io.print("\n===Current machine inventory===");
    }

    public void displayMachineInventory(List<InventoryItem> inventoryList){
        for (InventoryItem item : inventoryList){
            System.out.println(item.toString());
        }
        io.readString("Enter to continue");
    }

    public String getItemPurchasedTitle(){
        String selection =  io.readString(("Please enter the title of the item."));
        //accepted format is first letter:Uppercase [Coke], adjusted below to insure it matches
        selection = selection.toLowerCase();
        selection = selection.substring(0, 1).toUpperCase() + selection.substring(1).toLowerCase();
        return selection;
    }

    public BigDecimal getPaymentAmount(){
        String paid = io.readString("Enter deposit for item (X.XX), change will be calculated, if needed.");
        BigDecimal paidBD = new BigDecimal(paid);
        return paidBD;
    }

    public void displayPurchaseBanner(){
        io.print("===Purchase Item===");
    }

    public void displayExitBanner(){
        io.print("Exiting vending machine options.");
    }

    public void displayErrorMessage(String errorMsg){
        io.print("===ERROR===");
        io.print(errorMsg);
    }

    public void displayUnknownCommand(){
        io.print("===Invalid Selection===\n");
    }

    public void displayDeleteBanner(){ io.print("===Delete Inventory===\n"); }

    public void displayAddToInventoryBanner(){
        io.print("===Add Inventory Item===");
    }

    public void displayAddItemSuccessBanner(){
        io.print("===Successfully Added Item===");
    }

    public void displayDeleteSuccessfullBanner(){
        io.print("===Successfully Deleted Item===");
    }



    public InventoryItem getItemInfo(){
        String itemName = io.readString("Please enter inventory item name");
        String itemCost = io.readString("Please enter item cost");
        BigDecimal itemCostBD = new BigDecimal(itemCost);
        int itemInventoryNumber = io.readInt("Please enter item inventory number");
        InventoryItem newItem = new InventoryItem(itemName);
        newItem.setItemCost(itemCostBD);
        newItem.setNumberInInventory(itemInventoryNumber);
        return newItem;
    }

}

