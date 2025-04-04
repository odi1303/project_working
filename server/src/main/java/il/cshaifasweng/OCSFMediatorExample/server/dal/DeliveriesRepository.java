package il.cshaifasweng.OCSFMediatorExample.server.dal;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.Delivery;

import jakarta.transaction.Transactional;

@Transactional
@Repository
public interface DeliveriesRepository extends CrudRepository<Delivery, Long>
{
}