package il.cshaifasweng.OCSFMediatorExample.server.dal;

import jakarta.data.repository.CrudRepository;
import jakarta.data.repository.Repository;
import il.cshaifasweng.OCSFMediatorExample.server.dal.models.MenuItem;

import java.util.stream.Stream;

@Repository
public interface MenuRepository extends CrudRepository<MenuItem, Long>
{
}
