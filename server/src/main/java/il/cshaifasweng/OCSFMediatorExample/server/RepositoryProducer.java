
package il.cshaifasweng.OCSFMediatorExample.server;
import il.cshaifasweng.OCSFMediatorExample.server.dal.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@ApplicationScoped
public class RepositoryProducer {
    private static final AnnotationConfigApplicationContext context =
            new AnnotationConfigApplicationContext(SpringConfig.class);
/*
    @Produces
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
    }*/
    // Add this for EntityManager
    @Produces
    @ApplicationScoped
    public EntityManager produceEntityManager() {
        return context.getBean(EntityManagerFactory.class).createEntityManager();
    }
}
