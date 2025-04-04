package il.cshaifasweng.OCSFMediatorExample.server.bl;

import jakarta.data.repository.Repository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import il.cshaifasweng.OCSFMediatorExample.server.dal.RestaurantsRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.TableOrderRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.UsersRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.Restaurant;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.RestaurantTable;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.TableOrder;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.User;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
@ApplicationScoped
public class RestaurantsBL {
    public RestaurantsBL() {}
    @Inject
    RestaurantsRepository restaurantsRepository;

    @Inject
    UsersRepository usersRepository;

    @Inject
    TableOrderRepository tableOrderRepository;
/*
    public List<RestaurantSummery> getAllRestaurantsSummeries()
    {
        return restaurantsRepositroy.findAll().map(r -> new RestaurantSummery(
                r.getId(),
                r.getTables().stream().filter(RestaurantTable::isInside).mapToLong(RestaurantTable::getSize).sum(),
                r.getTables().stream().filter(t -> !t.isInside()).mapToLong(RestaurantTable::getSize).sum(),
                r.getSundayOpeningHours(),
                r.getMondayOpeningHours()
                r.getTuesdayOpeningHours(),
                r.getWednesdayOpeningHours(),
                r.getThursdayOpeningHours(),
                r.getFridayOpeningHours(),
                r.getSaturdayOpeningHours(),

                )).toList();
    }
*/
    public void orderTables(Long restaurantId, Long userId, boolean inside, Date startDate, Date endDate, Long amount) {
        User user = usersRepository.findById(userId).get();
        Restaurant restaurant = restaurantsRepository.findById(restaurantId).get();
        Stream<RestaurantTable> tablesInCorrectPlace = restaurant.getTables().stream().filter(t -> t.isInside() == inside);
        Stream<RestaurantTable> availableTables = tablesInCorrectPlace.filter(t -> t.getTableOrders().stream().allMatch(to -> endDate.before(to.getStartDate()) || startDate.after(to.getEndDate())));

        List<RestaurantTable> bigToSmall =
                availableTables
                        .sorted(Comparator.comparing(RestaurantTable::getSize).reversed())
                        .toList();


        Long remind = amount;
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
