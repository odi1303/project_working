package il.cshaifasweng.OCSFMediatorExample.server;
import il.cshaifasweng.OCSFMediatorExample.entities.Dish;
import il.cshaifasweng.OCSFMediatorExample.entities.Ingredient;
import il.cshaifasweng.OCSFMediatorExample.entities.PersonalPreference;
import il.cshaifasweng.OCSFMediatorExample.server.bl.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Database {
    private static Database db = null;
    private static Session session;

    private static AdminBL admins;
    private static ComplainsBL complaints;
    private static DeliveriesBL deliveries;
    private static MenuItemsBL menuItems;
    private static TableOrdersBL tableOrders;
    private static RestaurantsBL restaurants;
    private static DietitiansBL dietitians;

    private Database() {
        SessionFactory sessionFactory = getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
    }
    public static Database getInstance() {
        if (db == null) {
            db = new Database();
        }
        return db;
    }

    private static SessionFactory getSessionFactory() throws HibernateException {
        var config = new Configuration();
        config.addAnnotatedClass(Dish.class);
        config.addAnnotatedClass(Ingredient.class);
        config.addAnnotatedClass(PersonalPreference.class);

        var serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        return config.buildSessionFactory(serviceRegistry);
    }
}
