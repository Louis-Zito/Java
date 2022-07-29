package ui;
import dto.Order;
import dto.Product;
import dto.Tax;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface UserIO {

    void print(String msg);

    int readInt(String prompt);

    int readInt(String prompt, int min, int max);

    String readString(String prompt);

    void captureNewline();

    String readStateAbbreviation(String prompt);

    String readName(String prompt);

    BigDecimal readBigDecimalOver100(String prompt);

    Date readDateCheckFormat() throws ParseException;

    Date confirmFutureDateForOrder() throws ParseException;

    LocalDate convertDateToLocalDate(Date date) throws ParseException;

    String dateToString(Date date) throws ParseException;

    boolean readTaxList(List<Tax> taxList, String stateAbbrToFind);

    boolean readProductList(List<Product> productList, String productToFind);

    boolean readOrderList(List<Order> daysOrders, int orderNumber);
}
