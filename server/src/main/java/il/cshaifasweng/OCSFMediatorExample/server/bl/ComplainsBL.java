package il.cshaifasweng.OCSFMediatorExample.server.bl;

import jakarta.inject.Inject;
import il.cshaifasweng.OCSFMediatorExample.server.dal.ComplainsRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.DeliveriesRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.RestaurantsRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.UsersRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.Delivery;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.Restaurant;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.Complain;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.DeliveryComplain;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.RestaurantComplain;

import java.util.Date;
import java.util.Optional;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;

public class ComplainsBL {
    @Inject
    ComplainsRepository complainsRepository;

    @Inject
    UsersRepository usersRepository;

    @Inject
    DeliveriesRepository deliveriesRepository;

    @Inject
    RestaurantsRepository restaurantsRepository;

    public List<Complain> getAllComplains() {
        List<Complain> complains = complainsRepository.findAll().toList();
        return complains;
    }

    public List<DeliveryComplain> getDeliveryComplaints() {
        return complainsRepository.findAll()
                .filter(c -> c instanceof DeliveryComplain)
                .map(c -> (DeliveryComplain) c)
                .collect(Collectors.toList());
    }

    public List<RestaurantComplain> getRestaurantComplains() {
        return complainsRepository.findAll()
                .filter(c -> c instanceof RestaurantComplain)
                .map(c -> (RestaurantComplain) c)
                .collect(Collectors.toList());
    }

    public void createDeliveryComplain(Long userId, Long deliveryId, String description) {
        Optional<User> maybeUser = usersRepository.findById(userId);
        if (maybeUser.isEmpty()) {
            return;
        }
        User user = maybeUser.get();
        Optional<Delivery> optionalDelivery = deliveriesRepository.findById(deliveryId);
        if (optionalDelivery.isEmpty()) {
            return;
        }
        Delivery delivery= optionalDelivery.get();

        complainsRepository.insert(new DeliveryComplain(description, new Date(), user, delivery));
    }

    public void createRestaurantComplain(Long userId, Long restaurantId, String description) {
        Optional<User> maybeUser = usersRepository.findById(userId);
        if (maybeUser.isEmpty()) {
            return;
        }
        User user = maybeUser.get();
        Optional<Restaurant> optionalRestaurant = restaurantsRepository.findById(restaurantId);
        if (optionalRestaurant.isEmpty()) {
            return;
        }
        Restaurant restaurant = optionalRestaurant.get();

        complainsRepository.insert(new RestaurantComplain(description, new Date(), user, restaurant));
    }

    public void closeComplain(Long complainId) {
        Optional<Complain> maybeComplain = complainsRepository.findById(complainId);
        if (maybeComplain.isEmpty()) {
            return;
        }
        Complain complain = maybeComplain.get();

        complain.setAnsweredAt(new Date());

        complainsRepository.update(complain);
    }

    public void compensateComplain(Long complainId, Long compensation) {
        Optional<Complain> maybeComplain = complainsRepository.findById(complainId);
        if (maybeComplain.isEmpty()) {
            return;
        }
        Complain complain = maybeComplain.get();

        complain.setAnsweredAt(new Date());
        complain.setCompensation(compensation);

        complainsRepository.update(complain);
    }
}
