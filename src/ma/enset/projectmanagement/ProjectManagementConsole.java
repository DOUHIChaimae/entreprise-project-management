package ma.enset.projectmanagement;

import ma.enset.projectmanagement.services.CompositeServices;
import ma.enset.projectmanagement.services.Impl.CompositeServicesImpl;

import java.text.ParseException;

public class ProjectManagementConsole {
    public static void main(String[] args) throws ParseException {
        CompositeServices compositeServices = new CompositeServicesImpl();
        //compositeServices.exportProject(2);
        compositeServices.importerProject("2 eme projet");
    }
}
