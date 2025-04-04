package il.cshaifasweng.OCSFMediatorExample.server.bl;

import jakarta.data.repository.Repository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import il.cshaifasweng.OCSFMediatorExample.server.dal.DeliveriesRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.MenuRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.UsersRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.Delivery;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.DeliveryItem;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;

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
    DeliveriesRepository deliveriesRepository;

    @Inject
    MenuRepository menuRepository;

    /*public void createDelivery(Long userId, DeliveryAPI delivery) {
        User user = usersRepository.findById(userId).get();

        List<DeliveryItem> deliveryItems = delivery.getDeliveryItems().stream()
                .map(d -> new DeliveryItem(menuRepository.findById(d.getMenuItemId()).get(), d.getAmount()))
                .toList();

        deliveriesRepository.insert(new Delivery(delivery.getArriavl(), user, deliveryItems));
    }*/

    public void cancelDelivery(Long userId, Long deliveryId) {
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
