package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.bl.BasicUserBL;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import jakarta.enterprise.inject.spi.Bean;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.util.AnnotationLiteral;
import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App {

    private static SimpleServer server;

    public static void main(String[] args) throws IOException {
        AnnotationConfigApplicationContext springContext = new AnnotationConfigApplicationContext(SpringConfig.class);
        Weld weld = new Weld();
        WeldContainer container = weld.initialize();
        // Get BeanManager
        BeanManager beanManager = container.getBeanManager();

        // List all beans
        System.out.println("Listing all CDI beans:");
        for (Bean<?> bean : beanManager.getBeans(Object.class)) {
            System.out.println("Bean: " + bean.getBeanClass().getName() + ", Scope: " + bean.getScope().getName());
        }
        // Explicitly select BasicUserBL to test
        BasicUserBL basicUserBL = container.select(BasicUserBL.class).get();
        System.out.println("Manually selected BasicUserBL: " + basicUserBL);
        server = container.select(SimpleServer.class).get();
        System.out.println("Starting server...");
        server.listen();
    }

}
