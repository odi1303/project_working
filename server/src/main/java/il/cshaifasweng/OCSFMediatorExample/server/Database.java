package il.cshaifasweng.OCSFMediatorExample.server;
import il.cshaifasweng.OCSFMediatorExample.entities.Dish;
import il.cshaifasweng.OCSFMediatorExample.entities.Ingredient;
import il.cshaifasweng.OCSFMediatorExample.entities.PersonalPreference;
import il.cshaifasweng.OCSFMediatorExample.server.bl.*;
import jakarta.annotation.PostConstruct;
import jakarta.data.repository.Repository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
@ApplicationScoped
public class Database {
    // private static Session session;
    @Inject
    AdminBL admins;
    @Inject
    ComplainsBL complaints;
    @Inject
    DeliveriesBL deliveries;
    @Inject
    MenuItemsBL menuItems;
    @Inject
    TableOrdersBL tableOrders;
    @Inject
    RestaurantsBL restaurants;
    @Inject
    DietitiansBL dietitians;
    @Inject
    BasicUserBL basicUsers;

    public Database() {
        System.out.println("Database constructor called");
    }

    @PostConstruct
    public void init() {
        System.out.println("Database @PostConstruct:");
        System.out.println("  basicUsers: " + basicUsers);
        System.out.println("  dietitians: " + dietitians);
    }

    public BasicUserBL getBasicUsers() { return basicUsers; }
/*
    private static SessionFactory getSessionFactory() throws HibernateException {
        var config = new Configuration();
        config.addAnnotatedClass(Dish.class);
        config.addAnnotatedClass(Ingredient.class);
        config.addAnnotatedClass(PersonalPreference.class);

        var serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        return config.buildSessionFactory(serviceRegistry);
    }*/
}
