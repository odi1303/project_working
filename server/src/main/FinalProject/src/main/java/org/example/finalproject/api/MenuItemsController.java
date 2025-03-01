package org.example.finalproject.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import org.example.finalproject.dal.models.MenuItem;
import org.example.finalproject.bl.MenuItemsBL;

import java.util.List;

@Path("/menuitems")
public class MenuItemsController {
    @Inject
    MenuItemsBL menuItemsBL;
// https://www.google.com/search?q=hello
    @GET
    @Path("/")
    public List<MenuItem> getAllMenuItems(@QueryParam("restaurants") List<Long> restaurantIds) {
        return menuItemsBL.getAllMenuItems(restaurantIds);
    }
}
