package controller;

import dao.VendingMachinePersistenceException;
import dto.Change;
import dto.InventoryItem;
import service.*;
import ui.VendingMachineView;
import java.math.BigDecimal;
import java.util.List;

public class VendingMachineController {

    public VendingMachineController(VendingMachineServiceLayer service, VendingMachineView view){
        this.service = service;
        this.view = view;
    }

    private VendingMachineServiceLayer service;
    private VendingMachineView view;

    public void run(){
        boolean keepGoing = true;
        int menuSelection = 0;
            while(keepGoing){
                try{
                    displayInventory();
                    menuSelection = getMenuSelection();
                    switch(menuSelection){
                        case 1:
                            purchaseItemReturnChange();
                            break;
                        case 2:
                            keepGoing = false;
                            break;
                        case 3:
                            addToInventory();
                            break;
                        case 4:
                            deleteFromInventory();
                            break;
                    default:
                        unknownCommand();
                    }//end switch
                }//end try
                catch (NoItemInventoryException e) {
                    view.displayErrorMessage(e.getMessage());
                } catch (VendingMachinePersistenceException e) {
                    view.displayErrorMessage(e.getMessage());
                } catch (InsufficientFundsException e) {
                    view.displayErrorMessage(e.getMessage());
                }
            }//end while
        exitMessage();
    }//end run

    private void purchaseItemReturnChange() throws NoItemInventoryException, VendingMachinePersistenceException, InsufficientFundsException {
        purchaseMessage();
        String itemName = view.getItemPurchasedTitle();
        InventoryItem itemInStock = service.checkInventory(itemName);
        BigDecimal payment = view.getPaymentAmount();
        Change changeFromPurchase = service.purchaseItem(itemInStock, payment);
        view.displayChangeDue(changeFromPurchase);
    }

    private void addToInventory() throws VendingMachinePersistenceException{
        view.displayAddToInventoryBanner();
        InventoryItem item = view.getItemInfo();
        service.addItem(item);
        view.displayAddItemSuccessBanner();
    }

    private InventoryItem deleteFromInventory() throws VendingMachinePersistenceException{
        deleteMessage();
        String itemName = view.getItemPurchasedTitle();
        InventoryItem removedItem = service.removedInventoryItem(itemName);
        deleteSuccessfulMessage();
        return removedItem;
    }

    private void deleteSuccessfulMessage(){ view.displayDeleteSuccessfullBanner();};

    private void deleteMessage(){view.displayDeleteBanner();};

    private void purchaseMessage(){
        view.displayPurchaseBanner();
    }

    private void exitMessage(){
        view.displayExitBanner();
    }

    private void unknownCommand(){
        view.displayUnknownCommand();
    }

    private int getMenuSelection(){
        return view.printMachineOptionsGetSelection();
    }

    //STREAM USE
    private void displayInventory() throws VendingMachinePersistenceException{
        view.displayInventoryBanner();
        //service layer Lambda + Stream removes inventory = 0, returns list for view.display
        //DAO still contains inventory = 0 items for logs
        List<InventoryItem> itemList = service.getAvailableItems();
        view.displayMachineInventory(itemList);
    }
}//end class
