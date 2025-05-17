package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.server.dal.models.*;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.MenuItem;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.Complain;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.DeliveryComplain;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.RestaurantComplain;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.DeleteRequest;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.InsertRequest;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.Request;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.UpdateRequest;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.CreditInformation;
import jakarta.transaction.Transactional;
import javafx.util.Pair;
import org.hibernate.Session;
import il.cshaifasweng.OCSFMediatorExample.entities.*;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import jakarta.persistence.Query;


import org.hibernate.HibernateException;
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
        config.addAnnotatedClass(Complaint.class);
        config.addAnnotatedClass(Complain.class);
        config.addAnnotatedClass(RestaurantComplain.class);
        config.addAnnotatedClass(DeliveryComplain.class);
        config.addAnnotatedClass(LocationInformation.class);
        config.addAnnotatedClass(PersonalInformation.class);
        config.addAnnotatedClass(Reservation.class);
        config.addAnnotatedClass(OrderClient.class);
        config.addAnnotatedClass(OrderItem.class);
        config.addAnnotatedClass(ReservationDetails.class);
        config.addAnnotatedClass(CreditInformation.class);
        var serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        return config.buildSessionFactory(serviceRegistry);
    }

    public ManualDatabase() {
        try {
            SessionFactory sessionFactory = getSessionFactory();
            session = sessionFactory.openSession();
            initializeDataIfEmpty();
        } catch (Exception exception) {
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            System.err.println("An error occurred, changes have been rolled back.");
            exception.printStackTrace();
        }
    }

    private void initializeDataIfEmpty() {
        try {
            // Begin transaction
            session.beginTransaction();

            // Check if the database is empty (e.g., by querying the User table)
            Long complaintCount = (Long) session.createQuery("SELECT COUNT(*) FROM Complaint ").uniqueResult();
            if (complaintCount  > 0) {
                System.out.println("Database already contains data. Skipping initialization.");
                session.getTransaction().commit();
                return;
            }

            session.getTransaction().commit();
            System.out.println("Database initialized with default data.");
            generateOrders();
        } catch (Exception e) {
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            System.err.println("Failed to initialize data.");
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize data", e);
        }
    }
    @Transactional
    public void generateOrders(){
        session.beginTransaction();
        session.flush();
        MenuItem pizza = new MenuItem(
                "Pizza",
                "Cheese pizza with tomato sauce",
                35,
                "images/pizza.jpg",
                Arrays.asList("Branch A", "Branch B", "Branch C"),
                Arrays.asList("Cheese", "Tomato Sauce", "Dough"),
                new ArrayList<>(),
                10
        );
        session.save(pizza);

        MenuItem burger = new MenuItem(
                "Burger",
                "Beef burger with lettuce and tomato",
                40,
                "images/burger.jpg",
                Arrays.asList("Branch A", "Branch D"),
                Arrays.asList("Beef Patty", "Lettuce", "Tomato", "Bun"),
                new ArrayList<>(),
                0
        );
        session.save(burger);
        MenuItem pasta = new MenuItem(
                "Pasta",
                "Spaghetti with meatballs",
                30,
                "images/pasta.jpg",
                Arrays.asList("Branch B", "Branch C"),
                Arrays.asList("Spaghetti", "Meatballs", "Tomato Sauce"),
                new ArrayList<>(),
                5
        );
        session.save(pasta);
        MenuItem salad = new MenuItem(
                "Salad",
                "Fresh vegetable salad",
                25,
                "images/salad.jpg",
                Arrays.asList("Branch A", "Branch C"),
                Arrays.asList("Lettuce", "Tomato", "Cucumber", "Dressing"),
                new ArrayList<>(),
                15
        );
        session.save(salad);
        MenuItem sushi = new MenuItem(
                "Sushi",
                "Assorted sushi platter",
                55,
                "images/sushi.jpg",
                Arrays.asList("Branch D"),
                Arrays.asList("Rice", "Fish", "Seaweed", "Vegetables"),
                new ArrayList<>(),
                0
        );
        session.save(sushi);
        MenuItem hummusPlate = new MenuItem(
                "Hummus Plate",
                "Creamy hummus served with vegetables and pita.",
                25,
                "https://example.com/images/hummus.jpg",
                List.of("Haifa", "Tel Aviv"),
                List.of("Hummus", "Tomato", "Onion", "Olives"),
                new ArrayList<>(),
                0 // No Sale
        );
        session.save(hummusPlate);
        MenuItem falafelPlate = new MenuItem(
                "Falafel Plate",
                "Delicious falafel balls served with hummus and salad.",
                22,
                "https://example.com/images/falafel.jpg",
                List.of("Tel Aviv", "Haifa", "Jerusalem"),
                List.of("Falafel", "Hummus", "Lettuce", "Tomato"),
                new ArrayList<>(),
                15 // Sale: 15% off
        );
        session.save(falafelPlate);
        MenuItem cheeseSandwich = new MenuItem(
                "Cheese Sandwich",
                "A simple cheese sandwich with tomato and lettuce.",
                20,
                "https://example.com/images/cheese_sandwich.jpg",
                List.of("Haifa", "Jerusalem"),
                List.of("Cheese", "Tomato", "Lettuce"),
                new ArrayList<>(),
                0 // No Sale
        );
        session.save(cheeseSandwich);
        MenuItem beefSalad = new MenuItem(
                "Beef Salad",
                "Salad with grilled beef, lettuce, tomato, and cucumber.",
                40,
                "https://example.com/images/beef_salad.jpg",
                List.of("Tel Aviv", "Jerusalem"),
                List.of("Beef", "Lettuce", "Tomato", "Cucumber"),
                new ArrayList<>(),
                20 // Sale: 20% off
        );
        session.save(beefSalad);
        MenuItem falafelWrap = new MenuItem(
                "Falafel Wrap",
                "Falafel served in pita bread with lettuce and hummus.",
                24,
                "https://example.com/images/falafel_wrap.jpg",
                List.of("Haifa", "Tel Aviv"),
                List.of("Falafel", "Hummus", "Lettuce"),
                new ArrayList<>(),
                0 // No Sale
        );
        session.save(falafelWrap);
        MenuItem mixedPlatter = new MenuItem(
                "Mixed Platter",
                "Combination of falafel, hummus, tomato, and olives.",
                30,
                "https://example.com/images/mixed_platter.jpg",
                List.of("Tel Aviv", "Jerusalem"),
                List.of("Falafel", "Hummus", "Tomato", "Olives"),
                new ArrayList<>(),
                0 // No Sale
        );
        session.save(mixedPlatter);
        LocationInformation location1 = new LocationInformation("New York", "Broadway", "123");
        session.save(location1);
        LocationInformation location2 = new LocationInformation("Los Angeles", "Sunset Boulevard", "456");
        session.save(location2);
        LocationInformation location3 = new LocationInformation("Chicago", "Michigan Avenue", "789");
        session.save(location3);

        PersonalInformation personalInfo = new PersonalInformation("John Doe", "john@example.com", "1234567890");
        session.save(personalInfo);
        CreditInformation creditInfo = new CreditInformation("1234-5678-9012-3456", "12/27", "123");
        session.save(creditInfo);
        OrderClient order1 = new OrderClient(List.of(
                new OrderItem(pizza, 2),
                new OrderItem(burger, 1)
        ), true, location1, personalInfo, creditInfo);
        session.save(order1);
        OrderClient order2 = new OrderClient(List.of(
                new OrderItem(pasta, 1),
                new OrderItem(salad, 3)
        ), false, location2, personalInfo, creditInfo);
        session.save(order2);
        OrderClient order3 = new OrderClient(List.of(
                new OrderItem(sushi, 2),
                new OrderItem(pizza, 1),
                new OrderItem(salad, 1),
                new OrderItem(pasta, 3),
                new OrderItem(burger, 1),
                new OrderItem(salad, 3)
        ), true, location3, personalInfo, creditInfo);
        session.save(order3);

        ReservationDetails reservationDetails1 = new ReservationDetails(
                "Haifa Branch", "5", "Private Room", "2025-04-05", "18:00"
        );
        session.save(reservationDetails1);
        PersonalInformation personalInformation1 = new PersonalInformation(
                "Alice Johnson", "0521234567", "alice.johnson@example.com"
        );
        session.save(personalInformation1);
        CreditInformation creditInformation1 = new CreditInformation(
                "1234567812345678", "12/26", "123"
        );
        session.save(creditInformation1);
        Reservation reservation1 = new Reservation(reservationDetails1, personalInformation1, creditInformation1);
        session.save(reservation1);
        ReservationDetails reservationDetails2 = new ReservationDetails(
                "Tel Aviv Branch", "3", "Outdoor Area", "2025-04-06", "20:00"
        );
        session.save(reservationDetails2);
        PersonalInformation personalInformation2 = new PersonalInformation(
                "Bob Smith", "0549876543", "bob.smith@example.com"
        );
        session.save(personalInformation2);
        CreditInformation creditInformation2 = new CreditInformation(
                "8765432187654321", "05/27", "456"
        );
        session.save(creditInformation2);
        Reservation reservation2 = new Reservation(reservationDetails2, personalInformation2, creditInformation2);
        session.save(reservation2);
        ReservationDetails reservationDetails3 = new ReservationDetails(
                "Jerusalem Branch", "2", "VIP Lounge", "2025-04-07", "19:30"
        );
        session.save(reservationDetails3);
        PersonalInformation personalInformation3 = new PersonalInformation(
                "Charlie Brown", "0535556677", "charlie.brown@example.com"
        );
        session.save(personalInformation3);
        CreditInformation creditInformation3 = new CreditInformation(
                "4567891245678912", "08/28", "789"
        );
        session.save(creditInformation3);
        Reservation reservation3 = new Reservation(reservationDetails3, personalInformation3, creditInformation3);
        session.save(reservation3);
        session.flush();
        session.getTransaction().commit();
    }


    public void saveOrUpdate(Object o) {
        System.out.println("Saving " + o.getClass().getSimpleName());
        System.out.println(session);
        session.beginTransaction();
        System.out.println("a");
        try {
            session.saveOrUpdate(o);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
            Complaint existing = session.get(Complaint.class, ((Complaint) o).getId());
            System.out.println("11111111111111111111111111111111");
            if (existing == null) {
                session.save(o);
            } else {
                session.merge(o);  // or manually update the fields
            }
        }
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

    public Session getSession() {
        return session;
    }

}


class UsersBL {
    public static UserType getUserType(Session session, String name, String password) {
        System.out.println("hello from the database");
        session.beginTransaction();
        List<User> results = session.createQuery("FROM User u WHERE u.name = :name AND u.password = :password", User.class)
                .setParameter("name", name)
                .setParameter("password", password)
                .getResultList();

        User MaybeUser = results.isEmpty() ? null : results.get(0);
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
    public static List<Complaint> getAllComplains(Session session) {
        session.beginTransaction();
        var retval = session.createQuery("From Complaint", Complaint.class).getResultList();

        session.getTransaction().commit();
        session.flush();
        return retval;
    }
/*
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
*/
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
        //session.save(new DeliveryComplain(description, new Date(), user, delivery));
        session.getTransaction().commit();
    }
    public static void createDeliveryComplain(Session session,DeliveryComplain complain) {
        session.beginTransaction();
        //Optional<User> maybeUser = session.byId(User.class).loadOptional(userId);
        session.getTransaction().commit();
        session.beginTransaction();
        //Optional<Delivery> optionalDelivery = session.byId(Delivery.class).loadOptional(deliveryId);
        session.getTransaction().commit();
        /*if (optionalDelivery.isEmpty()) {
            return;
        }
        Delivery delivery = optionalDelivery.get();*/
        session.beginTransaction();
        session.save(complain);
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
        Optional<Complaint> maybeComplain = session.byId(Complaint.class).loadOptional(complainId);
        if (maybeComplain.isEmpty()) {
            return;
        }
        Complaint complain = maybeComplain.get();

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


        });*/
    }
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

    /*public static void cancelDelivery(Session session, Long userId, Long deliveryId) {
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
*/
    /*
    public Long createDelivery(Long userId, Long restaurantId, List<Long> menuItemIds, List<Long> amounts,boolean indoor) {
        // בדיקת תקינות האורך של הרשימות
        if (menuItemIds.size() != amounts.size()) {
            return 0L;
        }

        // שליפת המשתמש
        Optional<User> userOpt = usersRepository.findById(userId);
        if (userOpt.isEmpty()) return 0L;
        User user = userOpt.get();

        // שליפת המסעדה
        Optional<Restaurant> restaurantOpt = restaurantRepository.findById(restaurantId);
        if (restaurantOpt.isEmpty()) return 0L;
        Restaurant restaurant = restaurantOpt.get();

        // בניית רשימת פריטי משלוח
        List<DeliveryItem> deliveryItems = new ArrayList<>();


        for (int i = 0; i < menuItemIds.size(); i++) {
            Long menuItemId = menuItemIds.get(i);
            Long amount = amounts.get(i);

            Optional<MenuItem> menuItemOpt = menuRepository.findById(menuItemId);
            if (menuItemOpt.isEmpty()) return 0L;

            MenuItem menuItem = menuItemOpt.get();
            DeliveryItem deliveryItem = new DeliveryItem(menuItem, amount);

            deliveryItems.add(deliveryItem);
        }

        // תאריך נוכחי
        Date now = new Date();

        // יצירת המשלוח ושמירה
        Delivery delivery = new Delivery(now, user, deliveryItems, restaurant);
        deliveriesRepository.save(delivery);

        long cost = delivery.DeliveryPrice();
        restaurant.add_money(cost);

        return cost;
    }

// מחזיר כמה כסף מביאים ללקוח
    public Long cancelDelivery(Long userId, Long deliveryId) {
        Optional<User> Maybeuser = usersRepository.findById(userId);
        if(Maybeuser.isEmpty())
            return 0L;

        User user = Maybeuser.get();

        Delivery delivery = user.getDeliveries().stream().filter(d -> Objects.equals(d.getId(), deliveryId)).findFirst().orElseThrow();

        Date delivery_date = delivery.getArravilDate();
        Date now = new Date();
        Date delivery_dateBeforeHour = Date.from(delivery_date.toInstant().minus(1, ChronoUnit.HOURS));

        if (now.after(delivery_dateBeforeHour))
        {
            // Can't cancel
            return 0L;
        }
        long cost = delivery.DeliveryPrice();
        Date delivery_dateBefore3Hour = Date.from(delivery_date.toInstant().minus(3, ChronoUnit.HOURS));
        if (delivery_dateBefore3Hour.after(delivery.getArravilDate())) {
            return delivery.restaurant.return_money(cost);
            // return need to get money
        }
        long return_money = delivery.restaurant.return_money(cost/2);
        deliveriesRepository.delete(delivery);
        return return_money;
    }*/
}
