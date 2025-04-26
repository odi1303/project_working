/*
package il.cshaifasweng.OCSFMediatorExample.server.bl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.MenuItem;

import java.util.List;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@ApplicationScoped
public class MenuItemsBL {
    public MenuItemsBL() {}
    @Inject
    MenuRepository menuRepository;

    public MenuItemsBL(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public List<MenuItem> getAllMenuItems(List<Long> restaurantIds) {
        Stream<MenuItem> items = StreamSupport.stream(menuRepository.findAll().spliterator(), false);
        if (restaurantIds != null) {
            items = items.filter(m -> m.getRestaurantId() == null || restaurantIds.contains(m.getRestaurantId()));
        }
        return items.toList();
    }
}
*/
