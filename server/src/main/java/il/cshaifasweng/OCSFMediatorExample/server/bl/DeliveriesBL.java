package il.cshaifasweng.OCSFMediatorExample.server.bl;

import jakarta.data.repository.Repository;
import jakarta.enterprise.context.ApplicationScoped;

import jakarta.inject.Inject;
import il.cshaifasweng.OCSFMediatorExample.server.dal.DeliveriesRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.MenuRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.UsersRepository;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Objects;
@ApplicationScoped
public class DeliveriesBL {
    public DeliveriesBL() {}
    @Inject
    UsersRepository usersRepository;
    @Inject
    RestaurantsRepository restaurantRepository;

    @Inject
    DeliveriesRepository deliveriesRepository;

    @Inject
    MenuRepository menuRepository;


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
    }
}
