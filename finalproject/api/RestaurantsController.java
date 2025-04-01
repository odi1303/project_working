package org.example.finalproject.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import org.example.finalproject.api.models.RestaurantSummery;
import org.example.finalproject.bl.RestaurantsBL;

import java.util.List;

@Path("/restaurants")
public class RestaurantsController {
    @Inject
    RestaurantsBL restaurantsBL;

    @GET
    @Path("/")
    public List<RestaurantSummery> getRestaurantsSummeries()
    {
        return restaurantsBL.getAllRestaurantsSummeries();
    }

}
