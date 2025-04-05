package il.cshaifasweng.OCSFMediatorExample.server.bl;

import jakarta.inject.Inject;
import il.cshaifasweng.OCSFMediatorExample.server.dal.MenuRepository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.MenuItem;

import java.util.List;
import java.util.stream.Stream;

public class MenuItemsBL {

    MenuRepository menuRepository;

    public MenuItemsBL(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }
    public List<MenuItem> getAllMenuItems(List<Long> restaurantIds) {
        Stream<MenuItem> items = menuRepository.findAll();
        if (restaurantIds != null) {
            items = items.filter(m -> m.getRestaurantId() == null || restaurantIds.contains(m.getRestaurantId()));
        }
        return items.toList();
    }
}
