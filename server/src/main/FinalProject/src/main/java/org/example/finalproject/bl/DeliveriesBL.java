package org.example.finalproject.bl;

import jakarta.inject.Inject;
import org.example.finalproject.api.models.DeliveryAPI;
import org.example.finalproject.dal.DeliveriesRepository;
import org.example.finalproject.dal.MenuRepository;
import org.example.finalproject.dal.UsersRepository;
import org.example.finalproject.dal.models.Delivery;
import org.example.finalproject.dal.models.DeliveryItem;
import org.example.finalproject.dal.models.User;

import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

public class DeliveriesBL {
    @Inject
    UsersRepository usersRepository;

    @Inject
    DeliveriesRepository deliveriesRepository;

    @Inject
    MenuRepository menuRepository;

    public void createDelivery(int userId, DeliveryAPI delivery) {
        User user = usersRepository.findById(userId).get();

        List<DeliveryItem> deliveryItems = delivery.getDeliveryItems().stream()
                .map(d -> new DeliveryItem(menuRepository.findById(d.getMenuItemId()).get(), d.getAmount()))
                .toList();

        deliveriesRepository.insert(new Delivery(delivery.getArriavl(), user, deliveryItems));
    }

    public void cancelDelivery(int userId, long deliveryId) {
        User user = usersRepository.findById(userId).get();
        Delivery delivery = user.getDeliveries().stream().filter(d -> d.getId() == deliveryId).findFirst().get();

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
