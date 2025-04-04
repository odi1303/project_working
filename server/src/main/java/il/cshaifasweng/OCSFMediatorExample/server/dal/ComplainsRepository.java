package il.cshaifasweng.OCSFMediatorExample.server.dal;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.complains.Complain;

@Repository
public interface ComplainsRepository extends CrudRepository<Complain, Long>
{
}
