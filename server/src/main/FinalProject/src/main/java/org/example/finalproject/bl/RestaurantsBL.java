package org.example.finalproject.bl;

import jakarta.inject.Inject;
import org.example.finalproject.api.models.RestaurantSummery;
import org.example.finalproject.dal.RestaurantsRepositroy;
import org.example.finalproject.dal.TableOrderRepository;
import org.example.finalproject.dal.UsersRepository;
import org.example.finalproject.dal.models.Restaurant;
import org.example.finalproject.dal.models.RestaurantTable;
import org.example.finalproject.dal.models.TableOrder;
import org.example.finalproject.dal.models.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

public class RestaurantsBL {
    @Inject
    RestaurantsRepositroy restaurantsRepositroy;

    @Inject
    UsersRepository usersRepository;

    @Inject
    TableOrderRepository tableOrderRepository;

    public List<RestaurantSummery> getAllRestaurantsSummeries()
    {
        return restaurantsRepositroy.findAll().map(r -> new RestaurantSummery(
            r.getId(),
            r.getTables().stream().filter(RestaurantTable::isInside).mapToLong(RestaurantTable::getSize).sum(),
            r.getTables().stream().filter(t -> !t.isInside()).mapToLong(RestaurantTable::getSize).sum(),
                r.getSundayOpeningHours(),
                r.getMondayOpeningHours()
                // Fill other opening hours
        )).toList();
    }

    public void orderTables(long restaurantId, int userId, boolean inside, Date startDate, Date endDate, int amount) {
        User user = usersRepository.findById(userId).get();
        Restaurant restaurant = restaurantsRepositroy.findById(restaurantId).get();
        Stream<RestaurantTable> tablesInCorrectPlace = restaurant.getTables().stream().filter(t -> t.isInside() == inside);
        Stream<RestaurantTable> availableTables = tablesInCorrectPlace.filter(t -> t.getTableOrders().stream().allMatch(to -> endDate.before(to.getStartDate()) || startDate.after(to.getEndDate())));

        List<RestaurantTable> bigToSmall = availableTables.sorted(Comparator.comparingInt(RestaurantTable::getSize)).toList().reversed();

        int remind = amount;
        List<RestaurantTable> tablesToOrder = new ArrayList<>();
        for (RestaurantTable table : bigToSmall) {
            remind -= table.getSize();
            tablesToOrder.add(table);
            if (remind < 0) {
                break;
            }
        }

        if (remind > 0) {
            // not enough space
            return;
        }

        tableOrderRepository.insert(new TableOrder(startDate, endDate, user, tablesToOrder));
    }
}
