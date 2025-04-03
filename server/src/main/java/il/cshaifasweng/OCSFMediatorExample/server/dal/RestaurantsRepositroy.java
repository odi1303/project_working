package org.example.finalproject.dal;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import org.example.finalproject.dal.models.Restaurant;

@Repository
public interface RestaurantsRepositroy extends CrudRepository<Restaurant, Long>
{
}
