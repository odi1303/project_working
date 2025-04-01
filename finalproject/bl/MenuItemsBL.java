package org.example.finalproject.bl;

import jakarta.inject.Inject;
import org.example.finalproject.dal.MenuRepository;
import org.example.finalproject.dal.models.MenuItem;

import java.util.List;
import java.util.stream.Stream;

public class MenuItemsBL {
    @Inject
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
