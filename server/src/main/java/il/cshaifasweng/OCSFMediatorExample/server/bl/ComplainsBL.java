package org.example.finalproject.bl;

import jakarta.inject.Inject;
import org.example.finalproject.dal.ComplainsRepository;
import org.example.finalproject.dal.DeliveriesRepository;
import org.example.finalproject.dal.RestaurantsRepositroy;
import org.example.finalproject.dal.UsersRepository;
import org.example.finalproject.dal.models.Delivery;
import org.example.finalproject.dal.models.Restaurant;
import org.example.finalproject.dal.models.User;
import org.example.finalproject.dal.models.complains.Complain;
import org.example.finalproject.dal.models.complains.DeliveryComplain;
import org.example.finalproject.dal.models.complains.RestaurantComplain;

import java.util.Date;
import java.util.Optional;

public class ComplainsBL {
//    @Inject
    ComplainsRepository complainsRepository;

//    @Inject
    UsersRepository usersRepository;

//    @Inject
    DeliveriesRepository deliveriesRepository;

//    @Inject
    RestaurantsRepositroy restaurantsRepository;

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
        Complain comlain = complainsRepository.findById(complainId).get();

        comlain.setAnsweredAt(new Date());

        complainsRepository.update(comlain);
    }

    public void compensateComplain(Long complainId, Long compensation) {
        Complain comlain = complainsRepository.findById(complainId).get();

        comlain.setAnsweredAt(new Date());
        comlain.setCompensation(compensation);


        complainsRepository.update(comlain);
    }
}
