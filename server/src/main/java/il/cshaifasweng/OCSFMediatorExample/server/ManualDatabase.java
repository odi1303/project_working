package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.entities.models.*;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.Complain;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.DeliveryComplain;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.RestaurantComplain;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.DeleteRequest;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.InsertRequest;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.Request;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.UpdateRequest;
import il.cshaifasweng.OCSFMediatorExample.entities.models.User;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import il.cshaifasweng.OCSFMediatorExample.entities.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Stream;


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
        config.addAnnotatedClass(MenuServer.class);
        config.addAnnotatedClass(BranchEnt.class);
        config.addAnnotatedClass(TableOrder.class);
        config.addAnnotatedClass(Delivery.class);
        config.addAnnotatedClass(DeliveryItem.class);
        config.addAnnotatedClass(MenuItem.class);
        config.addAnnotatedClass(Restaurant.class);
        config.addAnnotatedClass(RestaurantTable.class);
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
            Long complaintCount = (Long) session.createQuery("SELECT COUNT(*) FROM MenuItem ").uniqueResult();
            if (complaintCount  > 0) {
                System.out.println("Database already contains data. Skipping initialization.");
                session.getTransaction().commit();
            }
            else {
                session.getTransaction().commit();
                System.out.println("Database initialized with default data.");
                generateData();
            }


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
    public void generateData() {
        session.beginTransaction();

        String foodImageUrl = "file:/C:/Users/sharo/Documents/Kiran_workspace/software_enginiring/project_working/client/src/main/resources/il/cshaifasweng/OCSFMediatorExample/client/menuItemPictures/FOOD_image.png";

        MenuItem pizza = new MenuItem(
                "Pizza",
                "Cheese pizza with tomato sauce",
                35,
                foodImageUrl,
                Arrays.asList("Kiryon", "Grand Kenyon"),
                Arrays.asList("Cheese", "Tomato Sauce", "Dough"),
                new ArrayList<>(),
                10
        );
        session.saveOrUpdate(pizza);

        /*Restaurant r = new Restaurant();
        r.name = "main branch";
        session.saveOrUpdate(r);*/
        MenuItem burger = new MenuItem(
                "Burger",
                "Beef burger with lettuce and tomato",
                40,
                foodImageUrl,
                Arrays.asList("Kiryon", "Grand Kenyon"),
                Arrays.asList("Beef Patty", "Lettuce", "Tomato", "Bun"),
                new ArrayList<>(),
                0
        );
        session.saveOrUpdate(burger);
        MenuItem pasta = new MenuItem(
                "Pasta",
                "Spaghetti with meatballs",
                30,
                foodImageUrl,
                Arrays.asList("Kiryon", "Grand Kenyon"),
                Arrays.asList("Spaghetti", "Meatballs", "Tomato Sauce"),
                new ArrayList<>(),
                5
        );
        session.saveOrUpdate(pasta);
        MenuItem salad = new MenuItem(
                "Salad",
                "Fresh vegetable salad",
                25,
                foodImageUrl,
                Arrays.asList("Kiryon", "Grand Kenyon"),
                Arrays.asList("Lettuce", "Tomato", "Cucumber", "Dressing"),
                new ArrayList<>(),
                15
        );
        session.saveOrUpdate(salad);
        MenuItem sushi = new MenuItem(
                "Sushi",
                "Assorted sushi platter",
                55,
                foodImageUrl,
                Arrays.asList("Kiryon"),
                Arrays.asList("Rice", "Fish", "Seaweed", "Vegetables"),
                new ArrayList<>(),
                0
        );
        session.saveOrUpdate(sushi);
        MenuItem hummusPlate = new MenuItem(
                "Hummus Plate",
                "Creamy hummus served with vegetables and pita.",
                25,
                foodImageUrl,
                List.of("Grand Kenyon"),
                List.of("Hummus", "Tomato", "Onion", "Olives"),
                new ArrayList<>(),
                0 // No Sale
        );
        session.saveOrUpdate(hummusPlate);
        MenuItem falafelPlate = new MenuItem(
                "Falafel Plate",
                "Delicious falafel balls served with hummus and salad.",
                22,
                foodImageUrl,
                List.of("Kiryon", "Grand Kenyon"),
                List.of("Falafel", "Hummus", "Lettuce", "Tomato"),
                new ArrayList<>(),
                15 // Sale: 15% off
        );
        session.saveOrUpdate(falafelPlate);
        MenuItem cheeseSandwich = new MenuItem(
                "Cheese Sandwich",
                "A simple cheese sandwich with tomato and lettuce.",
                20,
                foodImageUrl,
                List.of("Grand Kenyon"),
                List.of("Cheese", "Tomato", "Lettuce"),
                new ArrayList<>(),
                0 // No Sale
        );
        session.saveOrUpdate(cheeseSandwich);
        MenuItem beefSalad = new MenuItem(
                "Beef Salad",
                "Salad with grilled beef, lettuce, tomato, and cucumber.",
                40,
                foodImageUrl,
                List.of("Kiryon"),
                List.of("Beef", "Lettuce", "Tomato", "Cucumber"),
                new ArrayList<>(),
                20 // Sale: 20% off
        );
        session.saveOrUpdate(beefSalad);
        MenuItem falafelWrap = new MenuItem(
                "Falafel Wrap",
                "Falafel served in pita bread with lettuce and hummus.",
                24,
                foodImageUrl,
                List.of("Kiryon", "Grand Kenyon"),
                List.of("Falafel", "Hummus", "Lettuce"),
                new ArrayList<>(),
                0 // No Sale
        );
        session.saveOrUpdate(falafelWrap);
        MenuItem mixedPlatter = new MenuItem(
                "Mixed Platter",
                "Combination of falafel, hummus, tomato, and olives.",
                30,
                foodImageUrl,
                List.of("Kiryon", "Grand Kenyon"),
                List.of("Falafel", "Hummus", "Tomato", "Olives"),
                new ArrayList<>(),
                0 // No Sale
        );
        session.saveOrUpdate(mixedPlatter);
        LocationInformation location1 = new LocationInformation("New York", "Broadway", "123");
        session.saveOrUpdate(location1);
        LocationInformation location2 = new LocationInformation("Los Angeles", "Sunset Boulevard", "456");
        session.saveOrUpdate(location2);
        LocationInformation location3 = new LocationInformation("Chicago", "Michigan Avenue", "789");
        session.saveOrUpdate(location3);

        PersonalInformation personalInfo = new PersonalInformation("John Doe", "1234567890", "john@example.com");
        session.saveOrUpdate(personalInfo);
        CreditInformation creditInfo = new CreditInformation("1234567890123456", "12/27", "123");
        session.saveOrUpdate(creditInfo);
        OrderClient order1 = new OrderClient(List.of(
                new OrderItem(pizza, 2),
                new OrderItem(burger, 1)
        ), true, location1, personalInfo, creditInfo);
        session.saveOrUpdate(order1);
        OrderClient order2 = new OrderClient(List.of(
                new OrderItem(pasta, 1),
                new OrderItem(salad, 3)
        ), false, location2, personalInfo, creditInfo);
        session.saveOrUpdate(order2);
        OrderClient order3 = new OrderClient(List.of(
                new OrderItem(sushi, 2),
                new OrderItem(pizza, 1),
                new OrderItem(salad, 1),
                new OrderItem(pasta, 3),
                new OrderItem(burger, 1),
                new OrderItem(salad, 3)
        ), true, location3, personalInfo, creditInfo);
        session.saveOrUpdate(order3);

        ReservationDetails reservationDetails1 = new ReservationDetails(
                "Haifa Branch", "5", "Private Room", "2025-04-05", "18:00"
        );
        session.saveOrUpdate(reservationDetails1);
        PersonalInformation personalInformation1 = new PersonalInformation(
                "Alice Johnson", "0521234567", "alice.johnson@example.com"
        );
        session.saveOrUpdate(personalInformation1);
        CreditInformation creditInformation1 = new CreditInformation(
                "1234567812345678", "12/26", "123"
        );
        session.saveOrUpdate(creditInformation1);
        Reservation reservation1 = new Reservation(reservationDetails1, personalInformation1, creditInformation1);
        session.saveOrUpdate(reservation1);
        ReservationDetails reservationDetails2 = new ReservationDetails(
                "Kiryat Bialik Branch", "3", "Outdoor Area", "2025-04-06", "20:00"
        );
        session.saveOrUpdate(reservationDetails2);
        PersonalInformation personalInformation2 = new PersonalInformation(
                "Bob Smith", "0549876543", "bob.smith@example.com"
        );
        session.saveOrUpdate(personalInformation2);
        CreditInformation creditInformation2 = new CreditInformation(
                "8765432187654321", "05/27", "456"
        );
        session.saveOrUpdate(creditInformation2);
        Reservation reservation2 = new Reservation(reservationDetails2, personalInformation2, creditInformation2);
        session.saveOrUpdate(reservation2);
        ReservationDetails reservationDetails3 = new ReservationDetails(
                "Haifa Branch", "2", "VIP Lounge", "2025-04-07", "19:30"
        );
        session.saveOrUpdate(reservationDetails3);
        PersonalInformation personalInformation3 = new PersonalInformation(
                "Charlie Brown", "0535556677", "charlie.brown@example.com"
        );
        session.saveOrUpdate(personalInformation3);
        CreditInformation creditInformation3 = new CreditInformation(
                "4567891245678912", "08/28", "789"
        );
        session.saveOrUpdate(creditInformation3);
        Reservation reservation3 = new Reservation(reservationDetails3, personalInformation3, creditInformation3);
        session.saveOrUpdate(reservation3);



        // Create Main Menu with some dishes
        MenuServer mainMenu = new MenuServer("main menu", new ArrayList<>(List.of(
                pizza, burger, pasta, salad
        )));
        mainMenu.is_main_menu = true;
        session.saveOrUpdate(mainMenu);
/*
        // Create Vegan Menu
        MenuServer veganMenu = new MenuServer("Vegan Menu", new ArrayList<>(List.of(
                falafelPlate, hummusPlate, falafelWrap, mixedPlatter, salad
        )));
        session.saveOrUpdate(veganMenu);

        // Create Premium Menu
        MenuServer premiumMenu = new MenuServer("Premium Menu", new ArrayList<>(List.of(
                sushi, beefSalad, pasta, burger
        )));
        session.saveOrUpdate(premiumMenu);*/



// Reuse same hours for all days for simplicity
        OpeningHours sun = new OpeningHours(10L, 22L), mon = new OpeningHours(10L, 22L), tue = new OpeningHours(10L, 22L);
        OpeningHours wed = new OpeningHours(10L, 22L), thu = new OpeningHours(10L, 22L), fri = new OpeningHours(10L, 22L), sat = new OpeningHours(10L, 22L);

// Create sample tables
        RestaurantTable table1 = new RestaurantTable(2L, true, null); // Table 1 with 4 seats
        RestaurantTable table2 = new RestaurantTable(2L, true, null); // Table 2 with 6 seats
        RestaurantTable table3 = new RestaurantTable(2L, true, null); // Table 3 with 2 seats


        List<RestaurantTable> tables1 = createTablesList();
        List<RestaurantTable> tables2 = createTablesList();
        List<RestaurantTable> tables3 = createTablesList();

// Restaurant 1
        Restaurant r1 = new Restaurant(sun, mon, tue, wed, thu, fri, sat, tables1);
        r1.name = "Haifa Branch";
        for (RestaurantTable table : tables1){
            table.setBranch(r1);
        }
        table1.setBranch(r1);
        table2.setBranch(r1);
        table3.setBranch(r1);

        session.saveOrUpdate(table1);
        session.saveOrUpdate(table2);
        session.saveOrUpdate(table3);
        session.saveOrUpdate(r1);

// Restaurant 2
        OpeningHours sun1 = new OpeningHours(10L, 22L), mon1 = new OpeningHours(10L, 22L), tue1 = new OpeningHours(10L, 22L);
        OpeningHours wed1 = new OpeningHours(10L, 22L), thu1 = new OpeningHours(10L, 22L), fri1 = new OpeningHours(10L, 22L), sat1 = new OpeningHours(10L, 22L);
        Restaurant r2 = new Restaurant(sun1, mon1, tue1, wed1, thu1, fri1, sat1, tables2);
        r2.name = "Grand Kenyon";
        for (RestaurantTable table : tables2){
            table.setBranch(r2);
        }
        session.save(r2);


// Restaurant 3

        OpeningHours sun2 = new OpeningHours(10L, 22L), mon2 = new OpeningHours(10L, 22L), tue2 = new OpeningHours(10L, 22L);
        OpeningHours wed2 = new OpeningHours(10L, 22L), thu2 = new OpeningHours(10L, 22L), fri2 = new OpeningHours(10L, 22L), sat2 = new OpeningHours(10L, 22L);

        Restaurant r3 = new Restaurant(sun2, mon2, tue2, wed2, thu2, fri2, sat2, tables3);
        r3.name = "Kiryon";
        for (RestaurantTable table : tables3){
            table.setBranch(r3);
        }
        session.save(r3);


        session.flush();
        session.getTransaction().commit();
    }

    public void delete_object(Object o){
        if (!session.getTransaction().isActive())
            session.beginTransaction();
        if (o != null) {
            session.delete(o);
        }

        session.getTransaction().commit();
        session.flush();
    }

    public void saveOrUpdate(Object o) {
        System.out.println("Saving " + o.getClass().getSimpleName());
        System.out.println(session);
        if (!session.getTransaction().isActive())
            session.beginTransaction();
        else System.out.println("wtaf");
        System.out.println("a");

        try {
            if (o instanceof OrderClient){
                session.saveOrUpdate(((OrderClient) o).getCreditInformation());
                session.saveOrUpdate(((OrderClient) o).getLocationInformation());
                session.saveOrUpdate(((OrderClient) o).getPersonalInformation());
            }
            session.saveOrUpdate(o);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
            session.merge(o);
            /*Complaint existing = session.get(Complaint.class, ((Complaint) o).getId());
            if (existing == null) {
                session.save(o);
            } else {
                session.merge(o);// or manually update the fields
            }*/
        }
        System.out.println("b");
        try{
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        }
        System.out.println("c");
        try{
            if(session.getTransaction().isActive())
                session.flush();
            else {
                session.beginTransaction();
                session.flush();
            }
        }  catch (Exception e){
            e.printStackTrace();
        }
        System.out.println("Finished saving " + o.getClass().getSimpleName());
    }

    public <T> List<T> getAll(Class<T> clss) {
        if (!session.getTransaction().isActive()){
            session.beginTransaction();
        } else {
            System.out.println("wtf why is there an active transaction");
        }
        System.out.println("hello from get all "+clss.getSimpleName());
        var result = session.createQuery("FROM " + clss.getSimpleName(), clss).getResultList();
        session.getTransaction().commit();
        System.out.println(result.size());
        return result;
    }
    public <T> Optional<T> getById(Class<T> clss, Long id) {
        if (!session.getTransaction().isActive()){
            session.beginTransaction();
        } else {
            System.out.println("wtf why is there an active transaction");
        }
        var result = session.byId(clss).loadOptional(id);
        session.getTransaction().commit();
        return result;
    }
    public <T> Optional<T> getByNaturalId(Class<T> clss, String field ,Object natural_id) {
        if (!session.getTransaction().isActive()){
            session.beginTransaction();
        } else {
            System.out.println("wtf why is there an active transaction");
        }
        var result = session.byNaturalId(clss).using(field, natural_id).loadOptional();
        session.getTransaction().commit();
        return result;
    }

    public UserType getUserType(String name, String password) {
        return UsersBL.getUserType(session, name, password);
    }
    public OpeningTimes getOpeningTimes(String branch, LocalDate date) {
        return RestaurantsBL.getOpeningTimes(session, branch, date);
    }
    public ClosingTimes getClosingTimes(String branch, LocalDate date) {
        return RestaurantsBL.getClosingTimes(session, branch, date);
    }
    public boolean canReserve(ReservationDetails reservation_details, int hours_offset, int minutes_offset) {
        String branch = reservation_details.getBranch();
        boolean inside = reservation_details.isInside();
        int ppl = reservation_details.getGuestNumber();
        var start = reservation_details.getStartDateTime().plusHours(hours_offset).plusMinutes(minutes_offset);
        var tables = RestaurantsBL.getAvailableTables(session, branch, inside,start,start.plusHours(1).plusMinutes(30)).mapToInt(RestaurantTable::getSize).sorted().toArray();
        return RestaurantsBL.optimal_allocation(tables, ppl).isPresent();
    }
    public List<String> getAllIngredients() {
        return getAll(MenuItem.class)
                .stream()
                .flatMap(i -> i.getIngredients().stream())
                .distinct()
                .sorted()
                .toList();
    }
    public List<LocalDateTime> getAvailableTimes(ReservationDetails reservation_details) {
        String branch = reservation_details.getBranch();
        boolean inside = reservation_details.isInside();
        int ppl = reservation_details.getGuestNumber();
        var start = reservation_details.getStartDateTime();
        return RestaurantsBL.getReservationTimes(session, branch, inside, ppl, start);
    }
    public List<Complaint> getAllOpenComplaints() {
        return ComplaintsBL.getAllOpenComplains(session);
    }
    public Session getSession() {
        return session;
    }

    private List<RestaurantTable> createTablesList() {
        List<RestaurantTable> tables = new ArrayList<>();

        // Create tables with 2 seats
        for (int i = 0; i < 2; i++) {
            tables.add(new RestaurantTable(2L, true, null));
        }

        // Create tables with 3 seats
        for (int i = 0; i < 2; i++) {
            tables.add(new RestaurantTable(3L, true, null));
        }

        // Create tables with 4 seats
        for (int i = 0; i < 2; i++) {
            tables.add(new RestaurantTable(4L, true, null));
        }

        return tables;
    }

}


class UsersBL {
    public static UserType getUserType(Session session, String name, String password) {
        System.out.println("hello from the database");
        if (!session.getTransaction().isActive())
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
    public static List<Complaint> getAllOpenComplains(Session session) {
        if (!session.getTransaction().isActive())
            session.beginTransaction();
        var retval = session.createQuery("From Complaint Where Not handled", Complaint.class).getResultList();

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
        if (!session.getTransaction().isActive())
            session.beginTransaction();
        Optional<User> maybeUser = session.byId(User.class).loadOptional(userId);
        session.getTransaction().commit();
        if (maybeUser.isEmpty()) {
            return;
        }
        User user = maybeUser.get();
        if (!session.getTransaction().isActive())
            session.beginTransaction();
        Optional<Delivery> optionalDelivery = session.byId(Delivery.class).loadOptional(deliveryId);
        session.getTransaction().commit();
        if (optionalDelivery.isEmpty()) {
            return;
        }
        Delivery delivery = optionalDelivery.get();
        if (!session.getTransaction().isActive())
            session.beginTransaction();
        //session.save(new DeliveryComplain(description, new Date(), user, delivery));
        session.getTransaction().commit();
    }

    //wtf
    public static void createDeliveryComplain(Session session,DeliveryComplain complain) {
        if (!session.getTransaction().isActive())
            session.beginTransaction();
        //Optional<User> maybeUser = session.byId(User.class).loadOptional(userId);
        session.getTransaction().commit();
        if (!session.getTransaction().isActive())
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
        if (!session.getTransaction().isActive())
            session.beginTransaction();
        Optional<User> maybeUser = session.byId(User.class).loadOptional(userId);
        session.getTransaction().commit();
        if (maybeUser.isEmpty()) {
            return;
        }
        User user = maybeUser.get();
        if (!session.getTransaction().isActive())
            session.beginTransaction();
        Optional<Restaurant> optionalRestaurant = session.byId(Restaurant.class).loadOptional(restaurantId);
        session.getTransaction().commit();
        if (optionalRestaurant.isEmpty()) {
            return;
        }
        Restaurant restaurant = optionalRestaurant.get();

        if (!session.getTransaction().isActive())
            session.beginTransaction();
        session.save(new RestaurantComplain(description, new Date(), user, restaurant));
        session.getTransaction().commit();
    }

    public static void closeComplain(Session session, Long complainId) {
        //Optional<Complain> maybeComplain = complainsRepository.findById(complainId);
        if (!session.getTransaction().isActive())
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
        if (!session.getTransaction().isActive())
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
        if (!session.getTransaction().isActive())
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
        if (!session.getTransaction().isActive())
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
        if (!session.getTransaction().isActive())
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
class RestaurantsBL {
    public static OpeningTimes getOpeningTimes(Session session, String branch, LocalDate date) {
        System.out.println("hello from get opening times: ");
        final var opening_hours = session.byNaturalId(Restaurant.class).using("name", branch).loadOptional().map(r->r.getOpeningHours(date));
        System.out.println("hello from get opening times: "+opening_hours);
        return new OpeningTimes(branch, opening_hours.orElseThrow().startHour + ":00");
    }
    public static ClosingTimes getClosingTimes(Session session, String branch, LocalDate date) {
        System.out.println("hello from get closing times: ");
        final var opening_hours = session.byNaturalId(Restaurant.class).using("name", branch).loadOptional().map(r->r.getOpeningHours(date));
        System.out.println("hello from get closing times: "+opening_hours);
        return new ClosingTimes(branch, opening_hours.orElseThrow().endHour + ":00");
    }
    public static Stream<RestaurantTable> getAvailableTables(Session session, Long restaurantId, boolean inside, LocalDateTime startDate, LocalDateTime endDate) {
        return session.byId(Restaurant.class).load(restaurantId)
                .getTables()
                .stream()
                .filter(t -> t.isInside() == inside)
                .filter(t -> t.getTableOrders().stream().allMatch(to -> endDate.isBefore(to.getStartDate()) || startDate.isAfter(to.getEndDate())));
    }
    public static Stream<RestaurantTable> getAvailableTables(Session session, String branch, boolean inside, LocalDateTime startDate, LocalDateTime endDate) {
        return session.byNaturalId(Restaurant.class).using("name", branch)
                .load()
                .getTables()
                .stream()
                .filter(t -> t.isInside() == inside)
                .filter(t -> t.getTableOrders().stream().allMatch(to -> endDate.isBefore(to.getStartDate()) || startDate.isAfter(to.getEndDate())));
    }

    public static List<LocalDateTime> getReservationTimes(Session session, String branch, boolean inside, int ppl, LocalDateTime startDate) {
        final var restaurant = session.byNaturalId(Restaurant.class).using("name", branch).load();
        final var opening_hours = restaurant.getOpeningHours(startDate);
        final var end = startDate.toLocalDate().atTime(LocalTime.of((int)opening_hours.endHour,0));
        List<LocalDateTime> retval = new ArrayList<>();
        while (startDate.isBefore(end)) {
            final var finalStartDate = startDate;
            final var potential_end = startDate.plusHours(1).plusMinutes(30);
            final var finalEndDate = potential_end.isBefore(end) ? potential_end : end;
            final var tables = restaurant.getTables()
                    .stream()
                    .filter(t -> t.isInside() == inside)
                    .filter(t -> t.getTableOrders().stream().allMatch(to -> finalEndDate.isBefore(to.getStartDate()) || finalStartDate.isAfter(to.getEndDate())))
            .mapToInt(RestaurantTable::getSize).sorted().toArray();
            if (optimal_allocation(tables, ppl).isPresent()) retval.add(startDate);
            startDate = startDate.plusMinutes(15);
        }
        return retval;
    }

    static Optional<int[]> optimal_allocation(int[] tables, int ppl) {
        int total_tables = tables.length;
        int upper = ppl + 4;
        long[][] dp = new long[total_tables + 1][upper + 1];
        int[][] opt = new int[total_tables + 1][upper + 1];
        // int[] count = new int[3];
        // for (int t : tables) ++count[t - 2];

        Arrays.stream(opt).forEach(t -> Arrays.fill(t, -1));
        Arrays.fill(dp[0], Integer.MAX_VALUE);
        for (int c = 1; c <= total_tables; ++c) {
            int table = tables[c - 1];
            for (int r = 1; r <= ppl + 4; ++r) {
                if (table == r) {
                    dp[c][r] = 1;
                    opt[c][r] = table;
                } else if (table > r) dp[c][r] = dp[c - 1][r];
                else if (dp[c - 1][r] < dp[c - 1][r - table] + 1) dp[c][r] = dp[c - 1][r];
                else {
                    dp[c][r] = dp[c - 1][r - table] + 1;
                    opt[c][r] = table;
                }
            }
        }

        while (dp[total_tables][ppl] == Integer.MAX_VALUE && ppl < upper) ++ppl;
        if (ppl == upper) return Optional.empty();
        int[] optimal = new int[3];
        int j = ppl;
        for (int i = total_tables; i > 0; --i)
            if (opt[i][j] != -1) {
                ++optimal[opt[i][j] - 2];
                j -= opt[i][j];
            }
        return Optional.of(optimal);
    }


}