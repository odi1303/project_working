package il.cshaifasweng.OCSFMediatorExample.server.dal;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.Restaurant;

import jakarta.transaction.Transactional;

@Transactional
@Repository
public interface RestaurantsRepository extends CrudRepository<Restaurant, Long>
{
}
