
package il.cshaifasweng.OCSFMediatorExample.server;
import il.cshaifasweng.OCSFMediatorExample.server.dal.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@Dependent
public class RepositoryProducer {
    private static final AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(SpringConfig.class);
    @Autowired
    private EntityManagerFactory entityManagerFactory; // Inject Spring’s EMF

    @Produces
    @Dependent // Each injection gets a new EntityManager
    public EntityManager produceEntityManager() {
        EntityManager em = entityManagerFactory.createEntityManager();
        System.out.println("Producing EntityManager: " + em);
        return em;
    }
    /*@Produces
    @ApplicationScoped
    public UsersRepository produceUsersRepository() {
        return context.getBean(UsersRepository.class);
    }

    @Produces
    @ApplicationScoped
    public DeliveriesRepository produceDeliveriesRepository() {
        return context.getBean(DeliveriesRepository.class);
    }
    @Produces
    @ApplicationScoped
    public MenuRepository produceMenuRepository() {
        return context.getBean(MenuRepository.class);
    }
    @Produces
    @ApplicationScoped
    public RequestsRepository produceRequestsRepository() {
        return context.getBean(RequestsRepository.class);
    }
    @Produces
    @ApplicationScoped
    public RestaurantsRepository produceRestaurantsRepository() {
        return context.getBean(RestaurantsRepository.class);
    }
    @Produces
    @ApplicationScoped
    public TableOrderRepository produceTableOrderRepository() {
        return context.getBean(TableOrderRepository.class);
    }
    @Produces
    @ApplicationScoped
    public ComplainsRepository produceComplainsRepository() {
        return context.getBean(ComplainsRepository.class);
    }

    // Add this for EntityManager
@Produces
    @ApplicationScoped
    public EntityManager produceEntityManager() {
        return context.getBean(EntityManagerFactory.class).createEntityManager();
    }
*/
}
