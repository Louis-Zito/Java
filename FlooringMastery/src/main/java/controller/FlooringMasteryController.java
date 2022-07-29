package controller;
import dao.FlooringMasteryPersistenceException;
import dao.OrderDataPersistenceException;
import dao.ProductDataPersistenceException;
import dao.TaxDataPersistenceException;
import dto.Order;
import dto.Product;
import dto.Tax;
import service.FlooringMasteryServiceLayer;
import service.OrderNotFoundException;
import service.StateSalesNotAllowedException;
import service.ProductNotCurrentlySoldException;
import ui.FlooringMasteryView;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class FlooringMasteryController {

    private FlooringMasteryServiceLayer service;
    private FlooringMasteryView view;

    public FlooringMasteryController(FlooringMasteryServiceLayer service, FlooringMasteryView view){
        this.service = service;
        this.view = view;
    }

    public void run(){
        boolean keepGoing = true;
        int menuSelection = 0;
        while(keepGoing){
            try{
                menuSelection = view.printFlooringMasteryGetSelection();
                switch(menuSelection){
                    case 1:
                        String orderDate = getDateAsString();
                        displayDaysOrders(orderDate);
                        break;
                    case 2:
                        addOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        //view.captureNewline(); !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }//end switch
            }//end try
            catch (ParseException e) {
                view.displayErrorMessage(e.getMessage());
            } catch (OrderDataPersistenceException e){
                view.displayErrorMessage(e.getMessage());
            } catch (TaxDataPersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            } catch (StateSalesNotAllowedException e){
                view.displayErrorMessage(e.getMessage());
            } catch (ProductNotCurrentlySoldException e) {
                view.displayErrorMessage(e.getMessage());
            } catch (ProductDataPersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            } catch (OrderNotFoundException e){
                view.displayErrorMessage(e.getMessage());
            }  catch (IOException e){
                view.displayErrorMessage(e.getMessage());
            } catch (FlooringMasteryPersistenceException e) {
                view.displayErrorMessage(e.getMessage());
            }
        }//end while
    }//end run

    private String getDateAsString() throws ParseException {
        String orderDate = view.getDate();
        return orderDate;
    }

    private void displayDaysOrders(String date) throws OrderDataPersistenceException {
        List<Order> daysOrder = service.getDaysOrders(date);
        view.displayDaysOrders(daysOrder);
    }

    private Order addOrder() throws ParseException, TaxDataPersistenceException, StateSalesNotAllowedException, ProductNotCurrentlySoldException,
            ProductDataPersistenceException, OrderDataPersistenceException, IOException, FlooringMasteryPersistenceException {
        view.printAddOrderHeader();
        String addOrderDate = view.getDate();
        Order newOrder = view.addOrderNameStateProductArea();
        Tax newOrderTaxObject = service.getTaxIfValidStateElseNull(newOrder.getStateAbbreviation());
        if (newOrderTaxObject == null){
            throw new StateSalesNotAllowedException("Not a valid [STATE].");
        }
        Product newOrderProductObject = service.getProductIfValidElseNull(newOrder.getProductType());
        if (newOrderProductObject == null){
            throw new ProductNotCurrentlySoldException("Not a currently sold [PRODUCT].");
        }
        Order savedOrder = service.addOrder(newOrder, newOrderTaxObject, newOrderProductObject, addOrderDate);
        view.printSuccessfulAddHeader();
        return savedOrder;
    }

    private Order editOrder() throws ParseException, OrderDataPersistenceException, OrderNotFoundException, TaxDataPersistenceException,
            ProductDataPersistenceException, ProductNotCurrentlySoldException, StateSalesNotAllowedException, FlooringMasteryPersistenceException {
        view.printEditOrderHeader();
        String editedOrderDate = view.getDate();
        int orderNoToEdit = view.enterOrderNo();
        Order originalOrder = service.getOrderIfExistingOrder(orderNoToEdit, editedOrderDate);
        if (originalOrder == null) {
            throw new OrderNotFoundException("Order number for that date does not exist.");
        }
        List<Tax> currentTaxes = service.getCurrentTaxObjects();
        List<Product> currentProducts = service.getCurrentProductObjects();
        Order orderWithEdits = view.editOrderDetails(originalOrder, currentProducts, currentTaxes);
        Tax editedOrderTaxObject = service.getTaxIfValidStateElseNull(orderWithEdits.getStateAbbreviation());
        Product editedOrderProductObject = service.getProductIfValidElseNull(orderWithEdits.getProductType());
        orderWithEdits = service.orderCalculations(orderWithEdits, editedOrderTaxObject, editedOrderProductObject);


        //if no changes made, return original order
        if (originalOrder.equals(orderWithEdits)){
            System.out.println("No changes were made, keeping original order");
            return originalOrder;
        }

        //summary of edited order details shown
        System.out.println("Summary of changes after Order edits: ");
        view.printOrderHeader();
        System.out.println(orderWithEdits.toString());;

        //get final confirmation of order change: YES or NO
        String editOrderConfirmation = view.getEditConfirmation();

        //order edit confirmed: Yes = edited order to dao, No = original order unchanged
        if (editOrderConfirmation.equals("YES")){

            orderWithEdits = service.editOrder(orderWithEdits, editedOrderDate);
            view.printSuccessfulEditHeader();
            return orderWithEdits;
        }
        else{
            System.out.println("Order-edits canceled, keeping original order.");
            //TEST FOR NO CHANGE KEEP/DOUBLE RETURN
            return originalOrder;
        }
    }

    private Order removeOrder() throws ParseException, OrderDataPersistenceException, OrderNotFoundException, FlooringMasteryPersistenceException {
        view.printRemoveOrderHeader();
        String removeOrderDate = view.getDate();
        int orderNoToRemove = view.enterOrderNo();
        Order orderToRemoveIfFound = service.getOrderIfExistingOrder(orderNoToRemove, removeOrderDate);
        if (orderToRemoveIfFound == null){
            throw new OrderNotFoundException("Order number for that date does not exist.");
        }
        Order removedOrder = service.removeOrder(orderToRemoveIfFound, removeOrderDate);
        view.printSuccessfulRemoveHeader();
        return removedOrder;
    }

    private void unknownCommand(){
        view.displayUnknownCommand();
    }
}
