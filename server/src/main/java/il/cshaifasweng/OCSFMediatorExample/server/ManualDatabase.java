package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.dal.DeliveriesRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.*;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.MenuItem;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.Complain;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.DeliveryComplain;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.RestaurantComplain;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.DeleteRequest;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.InsertRequest;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.Request;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.UpdateRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.Session;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Dish;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class ManualDatabase {
    private Session session;
    private static SessionFactory getSessionFactory() throws HibernateException, IOException {
        var config = new Configuration();
        Scanner userInput = new Scanner(System.in);
        System.out.print("Enter the database password: ");
        String password = userInput.nextLine();
        config.setProperty("hibernate.connection.password", password);

        config.addAnnotatedClass(User.class);
        config.addAnnotatedClass(TableOrder.class);
        config.addAnnotatedClass(Delivery.class);
        config.addAnnotatedClass(DeliveryItem.class);
        config.addAnnotatedClass(MenuItem.class);
        config.addAnnotatedClass(Restaurant.class);
        config.addAnnotatedClass(RestaurantTable.class);
        config.addAnnotatedClass(TableOrder.class);
        config.addAnnotatedClass(OpeningHours.class);

        var serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        return config.buildSessionFactory(serviceRegistry);
    }

    public ManualDatabase() {
        try {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            /*session.beginTransaction();


            generateSampleMeals();
            printAllDishes();


            session.getTransaction().commit(); // Save everything*/
        } catch (Exception exception) {
            if (session != null) {
                session.getTransaction().rollback();
            }
            System.err.println("An error occurred, changes have been rolled back.");
            exception.printStackTrace();
        } finally {
            /*if (session != null) {
                session.close();
            }*/
        }
    }

    public void saveOrUpdate(Object o) {
        System.out.println("Saving " + o.getClass().getSimpleName());
        System.out.println(session);
        session.beginTransaction();
        System.out.println("a");
        session.saveOrUpdate(o);
        System.out.println("b");
        session.getTransaction().commit();
        System.out.println("c");
        session.flush();
        System.out.println("Finished saving " + o.getClass().getSimpleName());
    }

    public <T> List<T> getAll(T dummy) {
        session.beginTransaction();
        var result = session.createQuery("FROM " + dummy.getClass().getSimpleName()).getResultList();
        session.getTransaction().commit();
        return result;
    }

    public UserType getUserType(String name, String password) {
        return UsersBL.getUserType(session, name, password);
    }
}


class UsersBL {
    public static UserType getUserType(Session session, String name, String password) {
        System.out.println("hello from the database");
        session.beginTransaction();
        var MaybeUser = session.createQuery("FROM User u WHERE u.name = :name AND u.password = :password", User.class)
                .setParameter("name", name)
                .setParameter("password", password)
                .getSingleResultOrNull();
        session.getTransaction().commit();
        System.out.println("hello from the other side");
        if (MaybeUser == null) {
            return UserType.Empty;
        } else {
            return MaybeUser.type;
        }
    }
}

class ComplaintsBL {
    public static List<Complain> getAllComplains(Session session) {
        session.beginTransaction();
        var retval = session.createQuery("From Complain", Complain.class).getResultList();
        session.getTransaction().commit();
        return retval;
    }

    public static List<DeliveryComplain> getDeliveryComplaints(Session session) {
        return getAllComplains(session).stream()
                .filter(c -> c instanceof DeliveryComplain)
                .map(c -> (DeliveryComplain) c)
                .collect(Collectors.toList());
    }

    public static List<RestaurantComplain> getRestaurantComplains(Session session) {
        return getAllComplains(session).stream()
                .filter(c -> c instanceof RestaurantComplain)
                .map(c -> (RestaurantComplain) c)
                .collect(Collectors.toList());
    }

    public static void createDeliveryComplain(Session session, Long userId, Long deliveryId, String description) {
        session.beginTransaction();
        Optional<User> maybeUser = session.byId(User.class).loadOptional(userId);
        session.getTransaction().commit();
        if (maybeUser.isEmpty()) {
            return;
        }
        User user = maybeUser.get();
        session.beginTransaction();
        Optional<Delivery> optionalDelivery = session.byId(Delivery.class).loadOptional(deliveryId);
        session.getTransaction().commit();
        if (optionalDelivery.isEmpty()) {
            return;
        }
        Delivery delivery = optionalDelivery.get();
        session.beginTransaction();
        session.save(new DeliveryComplain(description, new Date(), user, delivery));
        session.getTransaction().commit();
    }

    public static void createRestaurantComplain(Session session, Long userId, Long restaurantId, String description) {
        session.beginTransaction();
        Optional<User> maybeUser = session.byId(User.class).loadOptional(userId);
        session.getTransaction().commit();
        if (maybeUser.isEmpty()) {
            return;
        }
        User user = maybeUser.get();
        session.beginTransaction();
        Optional<Restaurant> optionalRestaurant = session.byId(Restaurant.class).loadOptional(restaurantId);
        session.getTransaction().commit();
        if (optionalRestaurant.isEmpty()) {
            return;
        }
        Restaurant restaurant = optionalRestaurant.get();

        session.beginTransaction();
        session.save(new RestaurantComplain(description, new Date(), user, restaurant));
        session.getTransaction().commit();
    }

    public static void closeComplain(Session session, Long complainId) {
        //Optional<Complain> maybeComplain = complainsRepository.findById(complainId);
        session.beginTransaction();
        Optional<Complain> maybeComplain = session.byId(Complain.class).loadOptional(complainId);
        if (maybeComplain.isEmpty()) {
            return;
        }
        Complain complain = maybeComplain.get();

        complain.setAnsweredAt(new Date());

        session.save(complain);
        session.getTransaction().commit();
    }

    public static void compensateComplain(Session session, Long complainId, Long compensation) {
        //Optional<Complain> maybeComplain = complainsRepository.findById(complainId);
        session.beginTransaction();
        Optional<Complain> maybeComplain = session.byId(Complain.class).loadOptional(complainId);
        if (maybeComplain.isEmpty()) {
            return;
        }
        Complain complain = maybeComplain.get();

        complain.setAnsweredAt(new Date());
        complain.setCompensation(compensation);

        //complainsRepository.save(complain);
        session.save(complain);
        session.getTransaction().commit();
    }
}


class AdminsBL {
    public static void deleteMenuItem(Session session, Long menuId, Long userId)
    {
        session.beginTransaction();
        Optional<User> maybeUser = session.byId(User.class).loadOptional(userId);
        maybeUser.ifPresent(user -> {
            if (user.isAdmin()) {
                session.createQuery("DELETE FROM MenuItem WHERE id = :id")
                        .setParameter("id", menuId)
                        .executeUpdate();
            }
        });
        session.getTransaction().commit();

/*Optional<User> user = usersRepository.findById(userId);

        if (user.map(User::isAdmin).orElse(false)) {
            return;
        }

        menuRepository.deleteById(menuId);*/

    }


    public static void markRequestAsApproved(Session session, Long requestId, Long userId) {
        session.beginTransaction();
        Optional<User> maybeUser = session.byId(User.class).loadOptional(userId);
        /*Optional<User> user = usersRepository.findById(userId);

        if (user.map(User::isAdmin).orElse(false)) {
            return;
        }*/
        maybeUser.ifPresent(user -> {
            if (user.isAdmin()) {
                Optional<Request> maybeRequest = session.byId(Request.class).loadOptional(requestId);//requestsRepository.findById(requestId);
                maybeRequest.ifPresent(request -> {
                    switch (request) {
                        case DeleteRequest deleteRequest -> session.createQuery("DELETE FROM MenuItem WHERE id = :id")
                                .setParameter("id", deleteRequest.getMenuItem())
                                .executeUpdate();
                        //menuRepository.deleteById(deleteRequest.getMenuItem());
                        case InsertRequest insertRequest ->
                                session.save(new MenuItem(insertRequest.getMenuItemDescription(), insertRequest.getMenuItemPrice()));
                                //menuRepository.save(new MenuItem(insertRequest.getMenuItemDescription(), insertRequest.getMenuItemPrice()));
                        case UpdateRequest updateRequest ->
                                //menuRepository.findById(updateRequest.getMenuItem())
                                session.byId(MenuItem.class).loadOptional(updateRequest.getMenuItem())
                                        .ifPresent(menu -> {
                                    if (updateRequest.getMenuItemPrice() != null) {
                                        menu.setPrice(updateRequest.getMenuItemPrice());
                                    }
                                    if (updateRequest.getMenuItemDescription() != null) {
                                        menu.setDescription(updateRequest.getMenuItemDescription());
                                    }
                                    session.save(menu);
                                });
                        default -> {
                            // Unknown request
                        }
                    }
                    request.approve();
                    session.save(request);
                });
            }
        });
        session.getTransaction().commit();
        /*Optional<Request> maybeRequest = requestsRepository.findById(requestId);
        maybeRequest.ifPresent(request -> {
            switch (request) {
                case DeleteRequest deleteRequest -> menuRepository.deleteById(deleteRequest.getMenuItem());
                case InsertRequest insertRequest ->
                        menuRepository.save(new MenuItem(insertRequest.getMenuItemDescription(), insertRequest.getMenuItemPrice()));
                case UpdateRequest updateRequest ->
                        menuRepository.findById(updateRequest.getMenuItem()).ifPresent(menu -> {
                            if (updateRequest.getMenuItemPrice() != null) {
                                menu.setPrice(updateRequest.getMenuItemPrice());
                            }
                            if (updateRequest.getMenuItemDescription() != null) {
                                menu.setDescription(updateRequest.getMenuItemDescription());
                            }
                            menuRepository.save(menu);
                        });
                default -> {
                    // Unknown request
                }
            }
            request.approve();
            requestsRepository.save(request);


        });
    */}
    public static void markRequestAsRejected(Session session, Long requestId, Long userId) {
        session.beginTransaction();
        Optional<User> maybeUser = session.byId(User.class).loadOptional(userId);

        /*Optional<User> user = usersRepository.findById(userId);

        if (user.map(User::isAdmin).orElse(false)) {
            return;
        }*/

        maybeUser.ifPresent(user -> {
            if (user.isAdmin()) {
                Optional<Request> maybeRequest = session.byId(Request.class).loadOptional(requestId);//requestsRepository.findById(requestId);
                // Optional<Request> maybeRequest = requestsRepository.findById(requestId);
                maybeRequest.ifPresent(request -> {
                    request.reject();
                    session.save(request);
                });
            }
        });
        session.getTransaction().commit();
    }
}


class DeliveriesBL {
    /*public void createDelivery(Long userId, DeliveryAPI delivery) {
        User user = usersRepository.findById(userId).get();

        List<DeliveryItem> deliveryItems = delivery.getDeliveryItems().stream()
                .map(d -> new DeliveryItem(menuRepository.findById(d.getMenuItemId()).get(), d.getAmount()))
                .toList();

        deliveriesRepository.insert(new Delivery(delivery.getArriavl(), user, deliveryItems));
    }*/

    public static void cancelDelivery(Session session, Long userId, Long deliveryId) {
        User user = usersRepository.findById(userId).orElseThrow();
        Delivery delivery = user.getDeliveries().stream().filter(d -> Objects.equals(d.getId(), deliveryId)).findFirst().orElseThrow();

        Date now = new Date();
        Date nowBeforeHour = Date.from(now.toInstant().minus(1, ChronoUnit.HOURS));

        if (nowBeforeHour.after(delivery.getArravilDate())) {
            // Can't cancel
            return;
        }

        deliveriesRepository.delete(delivery);

        Date nowBefore3Hour = Date.from(now.toInstant().minus(3, ChronoUnit.HOURS));
        if (nowBefore3Hour.after(delivery.getArravilDate())) {
            // return need to get money
        }
    }
}
