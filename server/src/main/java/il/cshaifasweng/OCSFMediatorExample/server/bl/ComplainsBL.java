package il.cshaifasweng.OCSFMediatorExample.server.bl;

import il.cshaifasweng.OCSFMediatorExample.server.dal.models.MenuItem;
import jakarta.data.repository.Repository;
import jakarta.enterprise.context.ApplicationScoped;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.List;
import java.util.stream.Stream;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@ApplicationScoped
public class ComplainsBL {
    public ComplainsBL() {}
    @Inject
    ComplainsRepository complainsRepository;

    @Inject
    UsersRepository usersRepository;

    @Inject
    DeliveriesRepository deliveriesRepository;

    @Inject
    RestaurantsRepository restaurantsRepository;

    public List<Complain> getAllComplains() {
        var retval = new ArrayList<Complain>();
        complainsRepository.findAll().forEach(retval::add);
        return retval;
    }

    public List<DeliveryComplain> getDeliveryComplaints() {
        return StreamSupport.stream(complainsRepository.findAll().spliterator(),false)
                .filter(c -> c instanceof DeliveryComplain)
                .map(c -> (DeliveryComplain) c)
                .collect(Collectors.toList());
    }

    public List<RestaurantComplain> getRestaurantComplains() {
        return StreamSupport.stream(complainsRepository.findAll().spliterator(),false)
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

        complainsRepository.save(new DeliveryComplain(description, new Date(), user, delivery));
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

        complainsRepository.save(new RestaurantComplain(description, new Date(), user, restaurant));
    }

    public void closeComplain(Long complainId) {
        Optional<Complain> maybeComplain = complainsRepository.findById(complainId);
        if (maybeComplain.isEmpty()) {
            return;
        }
        Complain complain = maybeComplain.get();

        complain.setAnsweredAt(new Date());

        complainsRepository.save(complain);
    }

    public void compensateComplain(Long complainId, Long compensation) {
        Optional<Complain> maybeComplain = complainsRepository.findById(complainId);
        if (maybeComplain.isEmpty()) {
            return;
        }
        Complain complain = maybeComplain.get();

        complain.setAnsweredAt(new Date());
        complain.setCompensation(compensation);

        complainsRepository.save(complain);
    }
}
