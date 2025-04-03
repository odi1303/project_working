package il.cshaifasweng.OCSFMediatorExample.server.dal;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.requests.Request;

@Repository
public interface RequestsRepository extends CrudRepository<Request, Long>
{
}
