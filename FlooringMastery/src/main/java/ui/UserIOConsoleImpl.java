package ui;
import dto.Order;
import dto.Product;
import dto.Tax;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.math.BigDecimal;
import java.util.Date;
import java.util.regex.*;

public class UserIOConsoleImpl implements UserIO{

    final private Scanner console = new Scanner(System.in);

    @Override
    public void print(String msg) {
        System.out.println(msg);
    }

    @Override
    public int readInt(String prompt) {
        boolean invalidInput = true;
        int num = 0;
        while(invalidInput) {
            try {
                String stringValue = this.readString(prompt);
                num = Integer.parseInt(stringValue);
                invalidInput = false;
            } catch (NumberFormatException e) {
                this.print("Input error, must be an integer, please try again.");
            }
        }
        return num;
    }

    @Override
    public int readInt(String prompt, int min, int max){
        int result;
        do{
            result = readInt(prompt);
        } while (result < min || result > max);
    return result;
    }

    @Override
    public String readStateAbbreviation(String prompt) {
        boolean correctSize = false;
        String stateAbbreviation;
        do {
            stateAbbreviation = readString(prompt);
            if (stateAbbreviation == ""){
                break;
            }
            else if (stateAbbreviation.length() != 2) {
                System.out.println("State abbreviation must be two capital letters. Ex. \"NY\"");
                System.out.println("You entered: " + stateAbbreviation);
            } else {
                correctSize = true;
            }
        } while (!correctSize);
        stateAbbreviation = stateAbbreviation.toUpperCase();
        return stateAbbreviation;
    }

    @Override
    //returns a blank line
    public String readString(String prompt) {
        System.out.println(prompt);
        return console.nextLine();
    }

    //capture newline left after int output with order summaries
    @Override
    public void captureNewline(){
        console.nextLine();
    }

    @Override
    public String readName(String prompt) {
        boolean invalidInput = true;
        String customerName = "";
        //alphanumeric, comma, period and spaces accepted for customerName
        String pattern = "^[a-zA-Z0-9\\s.,]+";
        while (invalidInput) {
            customerName = readString(prompt);
            if (customerName == ""){
                return "";
            }
            if (Pattern.matches(pattern, customerName)){
                invalidInput = false;
            } else {
                System.out.println("Invalid [NAME] format");
                System.out.println("Name may only contain: a-z, 0-9, commas, periods and spaces.");
            }
        }
        return customerName;
    }

    @Override
    //BigDecimalOver100:
    //args: String for prompt
    //returns BigDecimal over 100
    //editOrder: String input = "" if not change, returns NULL
    public BigDecimal readBigDecimalOver100(String prompt) {
        boolean invalidInput = true;
        BigDecimal bd;

        String input = readString(prompt);
        if (input == ""){
            return null;
        }
        else{
            bd = new BigDecimal(input);
        }

        BigDecimal areaFloor100 = new BigDecimal("99.99");
            while(invalidInput){
                if ((areaFloor100.compareTo(bd)) > 0){
                    System.out.println("Invalid [AREA] entered.");
                    System.out.println("Area must be greater than, or equal to, 100.00");
                    input = readString("Re-enter [AREA]: ");
                    bd = new BigDecimal(input);
                } else {
                    invalidInput = false;
                }
            }//end while
            return bd;
        }


    @Override
    // checks Date for format: MM/dd/yyyy
    public Date readDateCheckFormat() throws ParseException{
        boolean dateValid;
        Date newDate = null;
        //regex pattern breakdown
        //month: 1 with (0-2) OR 0 with (1-9) + "/"
        //day: 3 with (O-1) OR (1-2) with (0-9) OR 0 with (1-9) + "/"
        //year: (0-9) occurring 4 times
        String datePattern = "^(1[0-2]|0[1-9])/(3[01]|[12][0-9]|0[1-9])/[0-9]{4}$";
        do {
            String date = this.readString("Enter date, acceptable format: MM/DD/YYYY.");
            dateValid = date.matches(datePattern);
            if(!dateValid){
                System.out.println("Invalid [DATE] format used entry.");
                System.out.println("Acceptable format: MM/DD/YYYY.");
            }
            else{
                System.out.println("Valid date format entered.");
                newDate = new SimpleDateFormat("MM/dd/yyyy").parse(date);
            }
        } while (!dateValid);
        return newDate;
    }//end date

    @Override
    //Check for date beyond today/in the future returning as Date object
    //includes Date format check
    public Date confirmFutureDateForOrder() throws ParseException{
        boolean isFutureDate = false;
        Date currentDay  = new Date();
        Date futureDate;
        do {
            //checking Date format
            futureDate = readDateCheckFormat();
            if(!(futureDate.after(currentDay))){
                System.out.println("Invalid [DATE] period entered.");
                Date tomorrow = new Date(currentDay.getTime() + (1000 * 60 * 60 * 24));
                System.out.println("Valid date must be " + dateToString(tomorrow) + ", or a later date.\n\n");
            }
            else{
                System.out.println("Valid date period confirmed.");
                isFutureDate = true;
            }

        } while (!isFutureDate);

        return futureDate;
    }

    @Override
    //converts LocalDate with time removed to String object for file names and output
    public String dateToString(Date date) throws ParseException{
        //remove time via convert to LocalDate method
        LocalDate localDate = convertDateToLocalDate(date);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        //convert LocalDate to String
        String dateStringMMDDYYYY = formatter.format(localDate);
        return dateStringMMDDYYYY;
    }

    @Override
    //removes time from date data via conversion to LocalDate object
    public LocalDate convertDateToLocalDate(Date date){
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }

    @Override
    public boolean readTaxList(List<Tax> taxList, String stateAbbrToFind) {
        for(Tax tax:taxList) if (tax.getStateAbbreviation().equals(stateAbbrToFind)) return true;
        return false;
    }

    @Override
    public boolean readProductList(List<Product> productList, String productToFind) {
        for(Product product:productList) if (product.getProductType().equals(productToFind)) return true;
        return false;
    }

    @Override
    public boolean readOrderList(List<Order> daysOrders, int orderNumber) {
        System.out.println("IO: readOrderList - daysOrders sent in: " + daysOrders);
        for (Order order : daysOrders) if (order.getOrderNumber() == orderNumber) return true;
        return false;
    }
}
