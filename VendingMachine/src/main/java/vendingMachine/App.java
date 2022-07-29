package vendingMachine;

import controller.VendingMachineController;
import dao.VendingMachineAuditDao;
import dao.VendingMachineAuditDaoFileImpl;
import dao.VendingMachineDao;
import dao.VendingMachineDaoFileImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.VendingMachineServiceLayer;
import service.VendingMachineServiceLayerImpl;

import ui.UserIO;
import ui.UserIOConsoleImpl;
import ui.VendingMachineView;

public class App {

    public static void main(String[] args) {
//        UserIO myIO = new UserIOConsoleImpl();
//        VendingMachineView myView = new VendingMachineView(myIO);
//        VendingMachineDao myDao = new VendingMachineDaoFileImpl();
//        VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoFileImpl();
//        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao, myAuditDao);
//        VendingMachineController controller = new VendingMachineController(myService, myView);
//        controller.run();

        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        VendingMachineController controller = ctx.getBean("controller", VendingMachineController.class);
        controller.run();
    }
}
