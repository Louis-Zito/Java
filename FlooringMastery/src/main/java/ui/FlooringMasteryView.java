package ui;
import dto.Order;
import dto.Product;
import dto.Tax;
import service.StateSalesNotAllowedException;
import service.ProductNotCurrentlySoldException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class FlooringMasteryView {

    public FlooringMasteryView(UserIO io){
        this.io = io;
    }

    private UserIO io;

    public int printFlooringMasteryGetSelection(){
        io.print("*********************************************************");
        io.print(" *<<Flooring Program>>");
        io.print(" 1. Display Orders");
        io.print(" 2. Add an Order");
        io.print(" 3. Edit an Order");
        io.print(" 4. Remove an Order");
        io.print(" 5. Quit");
        return io.readInt("Please select from the above options", 1, 5);
    }

    public int enterOrderNo(){
        int orderNo = 0;
        while (orderNo < 1) {
            orderNo = io.readInt("Enter order number: ");
            if (orderNo < 1) {
                io.print("ERROR: Order number must be 1 or greater.");
            }
        }
        return orderNo;
    }

    public void captureNewline(){
        io.captureNewline();
    }


    public String getDate() throws ParseException {
        //check format of Date object
        //Date newDate = io.readDateCheckFormat();
        Date newDate = io.confirmFutureDateForOrder();
        //lose time via localDate conversion and return as String
        String stringDate = io.dateToString(newDate);
        return stringDate;
    }

    public void printAddOrderHeader(){
        io.print("*********************************************************");
        io.print("<<Add Order>>");
    }

    public void printSuccessfulAddHeader(){
        io.print("*********************************************************");
        io.print("<<Successfully Added Order>>");
    }

    public void printEditOrderHeader(){
        io.print("*********************************************************");
        io.print("<<Edit Order>>");
    }

    public void printSuccessfulEditHeader(){
        io.print("*********************************************************");
        io.print("<<Successfully Edited Order>>");
    }

    public void printRemoveOrderHeader(){
        io.print("*********************************************************");
        io.print("<<Remove Order>>");
    }

    public void printSuccessfulRemoveHeader(){
        io.print("*********************************************************");
        io.print("<<Successfully Removed Order>>");
    }

    public void printOrderHeader(){
        io.print("*********************************************************");
        io.print("<<Order>>");
        io.print("Order-Number  Customer-Name  State  Tax-Rate  Product-Type  Area  Cost-Per-Sq.-Foot  Labor-Cost-per-Sq.-Foot  Material-Cost  Labor-Cost  Tax    TOTAL");
    }

    public Order addOrderNameStateProductArea() {
        String newOrderName = io.readName("Enter [CUSTOMER NAME] for new order: ");
        String newOrderState = io.readStateAbbreviation("Enter [STATE] for new order: ");
        String newProduct = io.readString("Enter [PRODUCT] being ordered: ");
        BigDecimal newOrderArea;
        newOrderArea = io.readBigDecimalOver100("Enter [AREA] of product needed: \nValue must be positive and 100 or more.");
        Order newOrder = new Order(0);
        newOrder.setCustomerName(newOrderName);
        newOrder.setStateAbbreviation(newOrderState);
        newOrder.setProductType(newProduct);
        newOrder.setArea(newOrderArea);
        return newOrder;
    }

    public void displayDaysOrders(List<Order> orderList){
        printOrderHeader();
        for (Order order : orderList){
            System.out.println(order.toString() + "\t   ");
        }
        io.readString("Enter to continue.");
    }

    public String getEditConfirmation(){
        boolean notAccepted = true;
        String editAnswer = "";
        editAnswer = io.readString("To keep edits, enter \"YES\", to keep original order details, enter \"NO\" : ");
        editAnswer.toUpperCase();

        while(notAccepted) {
            if ((editAnswer.equals("YES")) | (editAnswer.equals("NO"))) {
                notAccepted = false;
            } else {
                System.out.println("Only YES and NO are valid responses.");
                editAnswer = io.readString("Would you like to keep the edits made to your order?");
                editAnswer.toUpperCase();
            }
        }//end while
        return editAnswer;
    }

    public void printOrderDetails(Order order) {
        order.toString();
    }

    public Order editOrderDetails(Order order, List<Product> currentProducts, List<Tax> currentStateTaxes) throws StateSalesNotAllowedException, ProductNotCurrentlySoldException{
        io.print("*********************************************************");
        io.print("<<Edit future order>>");
        Order editedOrder = new Order(order.getOrderNumber());

        String editedName = io.readName("Edit [NAME] of order number " + order.getOrderNumber() + " (" + order.getCustomerName() + "):");
        editedName.toUpperCase();
        if (editedName != ""){
            editedName.toUpperCase();
            editedOrder.setCustomerName(editedName);
        }
        else {
            editedOrder.setCustomerName(order.getCustomerName());
        }

        String editedStateAbbreviation = io.readStateAbbreviation("Edit [STATE ABBREVIATION] of order number " + order.getOrderNumber() + " (" + order.getStateAbbreviation() + "):");
        if (editedStateAbbreviation != "") {
            boolean taxFound = io.readTaxList(currentStateTaxes, editedStateAbbreviation);
            if (!taxFound) {
                throw new StateSalesNotAllowedException("Not a valid state, sales not allowed.");
            } else {
                editedStateAbbreviation.toUpperCase();
                editedOrder.setStateAbbreviation(editedStateAbbreviation);
            }
        } else {
            editedOrder.setStateAbbreviation(order.getStateAbbreviation());
        }

        String editedProductType = io.readString("Edit [PRODUCT TYPE] of order number " + order.getOrderNumber() + "  (" + order.getProductType() + "):");
        if (editedProductType != ""){
            editedProductType = editedProductType.toUpperCase();
            boolean productFound = io.readProductList(currentProducts, editedProductType);
            if (!productFound){
                throw new ProductNotCurrentlySoldException("Product is not currently sold");
            }
            else {
                editedProductType.toUpperCase();
                editedOrder.setProductType(editedProductType);}
        } else {
            editedOrder.setProductType(order.getProductType());
        }

        BigDecimal editedArea = io.readBigDecimalOver100("Edit [AREA] of order number " + order.getOrderNumber() + "  (" + order.getArea() + "):");
        if (editedArea != null){
            editedOrder.setArea(editedArea);
        }
        else {
            editedOrder.setArea(order.getArea());
        }

        return editedOrder;
    }

    public void displayErrorMessage(String errorMsg){
        io.print("===ERROR===");
        io.print(errorMsg);
    }

    public void displayUnknownCommand(){
        io.print("===Invalid Selection===\n");
    }
}
