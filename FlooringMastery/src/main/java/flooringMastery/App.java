package flooringMastery;

import controller.FlooringMasteryController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
//        UserIO myIO =new UserIOConsoleImpl();
//        FlooringMasteryView myView = new FlooringMasteryView(myIO);
//        TaxDao myTaxDao = new TaxDaoFileImpl();
//        ProductDao myProductDao = new ProductDaoFileImpl();
//        OrderDao myOrderDao = new OrderDaoFileImpl();
//        FlooringMasteryAuditDao FMADao = new FlooringMasteryAudiDaoFileImpl();
//        FlooringMasteryServiceLayer myService = new FlooringMasterServiceLayerImpl(myOrderDao, myProductDao, myTaxDao, FMADao);
//        FlooringMasteryController controller = new FlooringMasteryController(myService, myView);
//        controller.run();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        FlooringMasteryController controller = ctx.getBean("controller", FlooringMasteryController.class);
        controller.run();
    }
}
